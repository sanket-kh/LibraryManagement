package com.librarymanagement.LibraryApplication.services.serviceImpls;

import com.library.fine.Dtos.FineCalculationRequest;
import com.library.fine.Dtos.FineDto;
import com.library.fine.responses.LibraryResponse;
import com.librarymanagement.LibraryApplication.LibraryApplication;
import com.librarymanagement.LibraryApplication.entities.Book;
import com.librarymanagement.LibraryApplication.entities.Fine;
import com.librarymanagement.LibraryApplication.entities.ReserveAndBorrow;
import com.librarymanagement.LibraryApplication.entities.User;
import com.librarymanagement.LibraryApplication.mappers.FineMapper;
import com.librarymanagement.LibraryApplication.mappers.ReserveAndBorrowMapper;
import com.librarymanagement.LibraryApplication.models.dtos.bookdtos.BorrowedBookDto;
import com.librarymanagement.LibraryApplication.models.dtos.bookdtos.ReservedBookDto;
import com.librarymanagement.LibraryApplication.models.requests.BorrowRequest;
import com.librarymanagement.LibraryApplication.models.requests.ReserveRequest;
import com.librarymanagement.LibraryApplication.repositories.BookRepo;
import com.librarymanagement.LibraryApplication.repositories.ReserveAndBurrowRepo;
import com.librarymanagement.LibraryApplication.repositories.UserRepo;
import com.librarymanagement.LibraryApplication.services.FineService;
import com.librarymanagement.LibraryApplication.services.ReserveAndBorrowService;
import com.librarymanagement.LibraryApplication.utils.Constants;
import com.librarymanagement.LibraryApplication.utils.ResponseConstants;
import com.librarymanagement.LibraryApplication.utils.ResponseUtility;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

@Log4j2
@Service
@AllArgsConstructor
@Transactional
public class ReserveAndBorrowServiceImpl implements ReserveAndBorrowService {
    private BookRepo bookRepo;
    private UserRepo userRepo;
    private ReserveAndBurrowRepo reserveAndBurrowRepo;
    private FineService fineService;

    @Override
    public ResponseEntity<Object> burrowBook(BorrowRequest borrowRequest) {
        try {
            User user = userRepo.findUserByUsername(borrowRequest.getUsername());
            if (user == null) {
                return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.NOT_FOUND, "Invalid username"), HttpStatus.OK);
            }
            if (!user.getStatus()) {
                return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.DISABLED_USER, "User is disabled"), HttpStatus.OK);
            }

            Book book = bookRepo.getBookByIsbn(borrowRequest.getIsbn());
            if (book == null) {
                return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.NOT_FOUND, "The book doesnt exist in the library"), HttpStatus.OK);
            }
            if (!book.getIsAvailable() || book.getCopies() == 0) {
                return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.NOT_FOUND, "The book is unavailable"), HttpStatus.OK);
            }
            ReserveAndBorrow existingTransaction = reserveAndBurrowRepo.findExistingTransactionByBookAndUser(book, user);
            if (existingTransaction != null) {
                return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.FORBIDDEN, "User cannot issue same book more than once"), HttpStatus.OK);
            }
            if (reserveAndBurrowRepo.findBorrowedBooksByUsername(user.getUsername()).size() >= Constants.BORROW_LIMIT_PER_USER) {
                return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.FORBIDDEN, "User has maximum number of books burrowed"), HttpStatus.OK);
            }
            ReserveAndBorrow reserveAndBorrow = new ReserveAndBorrow();
            reserveAndBorrow.setBook(book);
            reserveAndBorrow.setUser(user);
            reserveAndBorrow.setIssueDate(LocalDate.now());
            reserveAndBorrow.setIsIssued(Boolean.TRUE);
            book.setCopies(book.getCopies() - 1);

            bookRepo.save(book);
            reserveAndBurrowRepo.save(reserveAndBorrow);

            return new ResponseEntity<>(ResponseUtility.successResponseWithMessage(ResponseConstants.OK, "The book has been issued "), HttpStatus.OK);
        } catch (Exception e) {
            log.error("ReserveAndBorrowServiceImpl :: borrowBook ", e);
            return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.INTERNAL_ERROR, "Unable to borrow book"), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<Object> returnBook(BorrowRequest returnRequest) {
        try {
            User user = userRepo.findUserByUsername(returnRequest.getUsername());
            if (user == null) {
                return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.NOT_FOUND, "Invalid username"), HttpStatus.OK);
            }
            Book book = bookRepo.getBookByIsbn(returnRequest.getIsbn());
            if (book == null) {
                return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.NOT_FOUND, "The book doesnt exist in the library"), HttpStatus.OK);
            }
            ReserveAndBorrow existingTransaction = reserveAndBurrowRepo.findExistingTransactionByBookAndUser(book, user);
            if (existingTransaction == null) {
                return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.FORBIDDEN, "User has not burrowed this book"), HttpStatus.OK);
            }

            existingTransaction.setReturnDate(LocalDate.now());
            existingTransaction.setIsIssued(Boolean.FALSE);
            book.setCopies(book.getCopies() + 1);
            bookRepo.save(book);
            FineCalculationRequest fineCalculationRequest =
                    FineMapper.mapToFineCalculationRequest(ReserveAndBorrowMapper.mapToReserveAndBorrowDto(existingTransaction));

            LibraryResponse libraryResponse = fineService.calculateFine(fineCalculationRequest);


            FineDto fineDto = (FineDto) libraryResponse.getResponseBody();

            if(fineDto!=null){
                Fine fine = FineMapper.mapToFine(fineDto);
                existingTransaction.setFine(fine);
                fine.setReserveAndBorrow(existingTransaction);
            }

            reserveAndBurrowRepo.save(existingTransaction);

            return new ResponseEntity<>(ResponseUtility.successResponseWithMessage(ResponseConstants.OK, "Book returned. "+libraryResponse.getMessage()), HttpStatus.OK);
        } catch (Exception e) {
            log.error("ReserveAndBorrowServiceImpl :: returnBook ", e);
            return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.INTERNAL_ERROR, "Unable to return book"), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<Object> reserveUnavailableBook(ReserveRequest reserveRequest) {
        try {
            User user = userRepo.findUserByUsername(reserveRequest.getUsername());
            if (user == null) {
                return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.NOT_FOUND, "Invalid username"), HttpStatus.OK);
            }
            Book book = bookRepo.getBookByIsbn(reserveRequest.getIsbn());
            if (book == null) {
                return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.NOT_FOUND, "The book doesnt exist in the library"), HttpStatus.OK);
            }
            if (book.getCopies() != 0) {
                return new ResponseEntity<>(ResponseUtility.successResponseWithMessage(ResponseConstants.FORBIDDEN, "Cannot reserve available book. Borrow it instead"), HttpStatus.OK);
            }
            List<ReservedBookDto> reservedBookDtoList = reserveAndBurrowRepo.findReservedBooksByUsername(user.getUsername());
            if (reservedBookDtoList.size() > Constants.MAX_RESERVE_LIMIT_PER_USER) {
                return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.FORBIDDEN, "Maximum reservation has already been made by the user"), HttpStatus.OK);
            }
            ReserveAndBorrow reserveAndBorrow = new ReserveAndBorrow();
            reserveAndBorrow.setBook(book);
            reserveAndBorrow.setUser(user);
            reserveAndBorrow.setIsIssued(Boolean.FALSE);
            reserveAndBurrowRepo.save(reserveAndBorrow);

            return new ResponseEntity<>(ResponseUtility.successResponseWithMessage(ResponseConstants.OK, "The book has been reserved"), HttpStatus.OK);
        } catch (Exception e) {
            log.error("ReserveAndBorrowServiceImpl :: reserveUnavailableBook ", e);
            return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.INTERNAL_ERROR, "Unable to reserve book"), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<Object> cancelReservationOfBook(ReserveRequest cancleReserveRequest) {
        try {
            User user = userRepo.findUserByUsername(cancleReserveRequest.getUsername());
            if (user == null) {
                return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.NOT_FOUND, "Invalid username"), HttpStatus.OK);
            }
            Book book = bookRepo.getBookByIsbn(cancleReserveRequest.getIsbn());
            if (book == null) {
                return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.NOT_FOUND, "The book doesnt exist in the library"), HttpStatus.OK);
            }
            ReserveAndBorrow reserveAndBorrow = reserveAndBurrowRepo.findReservedTransactionByUserAndBook(user, book);
            if (reserveAndBorrow == null) {
                return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.NOT_FOUND, "User has not reserved the book"), HttpStatus.OK);
            }
            reserveAndBorrow.setReserved(Boolean.FALSE);
            return new ResponseEntity<>(ResponseUtility.successResponseWithMessage(ResponseConstants.OK, "Reservation canceled"), HttpStatus.OK);
        } catch (Exception e) {
            log.error("ReserveAndBorrowServiceImpl :: cancelReservationOfBook ", e);
            return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.INTERNAL_ERROR, "Unable to cancel reservation of book"), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<Object> viewBurrowedBooksByUser(String username) {
        try {
            User user = userRepo.findUserByUsername(username);
            if (user == null) {
                return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.NOT_FOUND, "Invalid username"), HttpStatus.OK);
            }
            List<BorrowedBookDto> borrowedBookList = reserveAndBurrowRepo.findBorrowedBooksByUsername(username);

            if (borrowedBookList.isEmpty()) {
                return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.NOT_FOUND, "User has not borrowed any book"), HttpStatus.OK);
            }
            return new ResponseEntity<>(ResponseUtility.failureResponseWithMessageAndBody(ResponseConstants.OK, "List of books retrieved", borrowedBookList), HttpStatus.OK);
        } catch (Exception e) {
            log.error("ReserveAndBorrowServiceImpl :: viewBurrowedBooksByUser ", e);
            return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.INTERNAL_ERROR, "Unable to view books reserved by user"), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<Object> viewReservedBooksByUser(String username) {
        try {
            User user = userRepo.findUserByUsername(username);
            if (user == null) {
                return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.NOT_FOUND, "Invalid username"), HttpStatus.OK);
            }
            List<ReservedBookDto> reservedBookDtoList = reserveAndBurrowRepo.findReservedBooksByUsername(username);

            if (reservedBookDtoList.isEmpty()) {
                return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.NOT_FOUND, "User has not reserved any book"), HttpStatus.OK);
            }
            return new ResponseEntity<>(ResponseUtility.successResponseWithMessageAndBody(ResponseConstants.OK, "List of books " +
                    "retrieved", reservedBookDtoList), HttpStatus.OK);
        } catch (Exception e) {
            log.error("ReserveAndBorrowServiceImpl :: viewReservedBooksByUser ", e);
            return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.INTERNAL_ERROR, "Unable to view books borrowed by user"), HttpStatus.OK);
        }
    }
}
