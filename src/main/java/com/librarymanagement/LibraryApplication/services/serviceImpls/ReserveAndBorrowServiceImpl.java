package com.librarymanagement.LibraryApplication.services.serviceImpls;

import com.library.fine.Dtos.FineCalculationRequest;
import com.library.fine.Dtos.FineDto;
import com.library.fine.responses.LibraryResponse;
import com.librarymanagement.LibraryApplication.entities.Book;
import com.librarymanagement.LibraryApplication.entities.Fine;
import com.librarymanagement.LibraryApplication.entities.ReserveAndBorrow;
import com.librarymanagement.LibraryApplication.entities.User;
import com.librarymanagement.LibraryApplication.mappers.FineMapper;
import com.librarymanagement.LibraryApplication.mappers.ReserveAndBorrowMapper;
import com.librarymanagement.LibraryApplication.models.dtos.BookTransactionsDto;
import com.librarymanagement.LibraryApplication.models.dtos.BorrowedBookDto;
import com.librarymanagement.LibraryApplication.models.dtos.UserBookTransaction;
import com.librarymanagement.LibraryApplication.models.requests.BorrowRequest;
import com.librarymanagement.LibraryApplication.models.requests.TransactionSearchReq;
import com.librarymanagement.LibraryApplication.repositories.BookRepo;
import com.librarymanagement.LibraryApplication.repositories.ReserveAndBurrowRepo;
import com.librarymanagement.LibraryApplication.repositories.UserRepo;
import com.librarymanagement.LibraryApplication.services.FineService;
import com.librarymanagement.LibraryApplication.services.ReserveAndBorrowService;
import com.librarymanagement.LibraryApplication.utils.Constants;
import com.librarymanagement.LibraryApplication.utils.ResponseConstants;
import com.librarymanagement.LibraryApplication.utils.ResponseUtility;
import com.librarymanagement.LibraryApplication.utils.Utils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional(rollbackOn = Exception.class)
public class ReserveAndBorrowServiceImpl implements ReserveAndBorrowService {
    private final BookRepo bookRepo;
    private final UserRepo userRepo;
    private final ReserveAndBurrowRepo reserveAndBurrowRepo;
    private final FineService fineService;

    @Override
    public ResponseEntity<Object> burrowBook(BorrowRequest borrowRequest) {
        try {
            User user = userRepo.findUserByUsername(Utils.getUsernameFromContext());
            Book book = bookRepo.getBookByIsbn(borrowRequest.getIsbn());
            if (book == null) {
                return ResponseUtility.failureResponseWithMessage(ResponseConstants.NOT_FOUND,
                        "The book doesnt exist in the library", HttpStatus.NOT_FOUND);
            }
            if (!book.getIsAvailable() || book.getCopies() == 0) {
                return ResponseUtility.failureResponseWithMessage(ResponseConstants.NOT_FOUND,
                        "The book is unavailable", HttpStatus.CONFLICT);
            }
            ReserveAndBorrow existingTransaction =
                    reserveAndBurrowRepo.findExistingTransactionByBookAndUser(book, user);
            if (existingTransaction != null) {
                return ResponseUtility.failureResponseWithMessage(ResponseConstants.FORBIDDEN,
                        "You cannot issue same book more than once", HttpStatus.CONFLICT);
            }
            if (reserveAndBurrowRepo.findBorrowedBooksByUsername(user.getUsername())
                        .size() >= Constants.BORROW_LIMIT_PER_USER) {
                return ResponseUtility.failureResponseWithMessage(ResponseConstants.FORBIDDEN,
                        "You have maximum number of books burrowed", HttpStatus.FORBIDDEN);
            }
            ReserveAndBorrow reserveAndBorrow = new ReserveAndBorrow();
            reserveAndBorrow.setBook(book);
            reserveAndBorrow.setUser(user);
            reserveAndBorrow.setIssueDate(LocalDateTime.now());
            reserveAndBorrow.setIsIssued(Boolean.TRUE);
            book.setCopies(book.getCopies() - 1);

            bookRepo.save(book);
            reserveAndBurrowRepo.save(reserveAndBorrow);

            return ResponseUtility.successResponseWithMessage(ResponseConstants.OK,
                    "The book has been issued", HttpStatus.OK);
        } catch (Exception e) {
            log.error("ReserveAndBorrowServiceImpl :: borrowBook ", e);
            return ResponseUtility.failureResponseWithMessage(ResponseConstants.INTERNAL_ERROR,
                    "Unable to borrow book", HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<Object> returnBook(BorrowRequest returnRequest) {
        try {
            User user = userRepo.findUserByUsername(Utils.getUsernameFromContext());
            Book book = bookRepo.getBookByIsbn(returnRequest.getIsbn());
            if (book == null) {
                return ResponseUtility.failureResponseWithMessage(ResponseConstants.NOT_FOUND,
                        "The book doesnt exist in the library", HttpStatus.OK);
            }
            ReserveAndBorrow existingTransaction =
                    reserveAndBurrowRepo.findExistingTransactionByBookAndUser(book, user);
            if (existingTransaction == null) {
                return ResponseUtility.failureResponseWithMessage(ResponseConstants.FORBIDDEN,
                        "User has not burrowed this book", HttpStatus.OK);
            }

            existingTransaction.setReturnDate(LocalDateTime.now());
            existingTransaction.setIsIssued(Boolean.FALSE);
            book.setCopies(book.getCopies() + 1);
            bookRepo.save(book);
            FineCalculationRequest fineCalculationRequest = FineMapper.mapToFineCalculationRequest(
                    ReserveAndBorrowMapper.mapToReserveAndBorrowDto(existingTransaction));

            LibraryResponse libraryResponse = fineService.calculateFine(fineCalculationRequest);


            FineDto fineDto = (FineDto) libraryResponse.getResponseBody();

            if (fineDto != null) {
                Fine fine = FineMapper.mapToFine(fineDto);
                existingTransaction.setFine(fine);
                fine.setReserveAndBorrow(existingTransaction);
            }

            reserveAndBurrowRepo.save(existingTransaction);

            return ResponseUtility.successResponseWithMessage(ResponseConstants.OK,
                    "Book returned." + libraryResponse.getMessage(), HttpStatus.OK);
        } catch (Exception e) {
            log.error("ReserveAndBorrowServiceImpl :: returnBook ", e);
            return ResponseUtility.failureResponseWithMessage(ResponseConstants.INTERNAL_ERROR,
                    "Unable to return book", HttpStatus.OK);
        }
    }


    @Override
    public ResponseEntity<Object> viewBurrowedBooksByUser(String username) {
        try {
            User user = userRepo.findUserByUsername(username);
            if (user == null) {
                return ResponseUtility.failureResponseWithMessage(ResponseConstants.NOT_FOUND,
                        "Invalid username", HttpStatus.OK);
            }
            List<BorrowedBookDto> borrowedBookList =
                    reserveAndBurrowRepo.findBorrowedBooksByUsername(username);

            if (borrowedBookList.isEmpty()) {
                return ResponseUtility.failureResponseWithMessage(ResponseConstants.NOT_FOUND,
                        "User has not borrowed any book", HttpStatus.OK);
            }
            borrowedBookList.forEach(borrowedBookDto -> {
                long overdue = ChronoUnit.DAYS.between(borrowedBookDto.getIssuedDate()
                        .plusDays(Constants.MAX_BORROW_DURATION_PER_BOOK), LocalDateTime.now());
                borrowedBookDto.setOverdue((int) overdue);
            });
            return ResponseUtility.failureResponseWithMessageAndBody(ResponseConstants.OK,
                    "List of books retrieved", borrowedBookList, HttpStatus.OK);
        } catch (Exception e) {
            log.error("ReserveAndBorrowServiceImpl :: viewBurrowedBooksByUser ", e);
            return ResponseUtility.failureResponseWithMessage(ResponseConstants.INTERNAL_ERROR,
                    "Unable to view books reserved by user", HttpStatus.OK);
        }
    }


    @Override
    public ResponseEntity<Object> getUserTransaction(String username, Integer pageSize,
                                                     Integer pageNo) {
        try {
            User user = this.userRepo.findUserByUsername(username);
            if (user == null) {
                return ResponseUtility.failureResponseWithMessage(ResponseConstants.NOT_FOUND,
                        "Invalid User", HttpStatus.UNAUTHORIZED);
            }
            Pageable pageable =
                    PageRequest.of(pageNo, pageSize, Sort.by("returnDate").descending());
            Page<UserBookTransaction> page =
                    reserveAndBurrowRepo.getAllActiveUserTransaction(pageable, username);
            List<UserBookTransaction> userBookTransactions = page.getContent();
            if (userBookTransactions.isEmpty()) {
                return ResponseUtility.successResponseWithMessage(ResponseConstants.NOT_FOUND,
                        "No " + "transaction done yet", HttpStatus.OK);
            }
            return ResponseUtility.successResponseWithMessageAndBody(ResponseConstants.OK,
                    "User " + "transactions fetched", userBookTransactions, HttpStatus.OK);
        } catch (Exception e) {
            log.error("ReserveAndBorrowServiceImpl :: getUserTransaction", e);
            return ResponseUtility.failureResponseWithMessage(ResponseConstants.INTERNAL_ERROR,
                    "Some error occurred", HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    @Override
    public ResponseEntity<Object> getAllTransactions(Integer pageNo, Integer pageSize) {
        try {
            Pageable pageable =
                    PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "returnDate"));
            Page<BookTransactionsDto> allTransactions =
                    reserveAndBurrowRepo.getAllTransaction(pageable);
            if (allTransactions.isEmpty()) {
                return ResponseUtility.failureResponseWithMessage(ResponseConstants.NOT_FOUND,
                        "No " + "transaction done yet", HttpStatus.NOT_FOUND);
            }
            return ResponseUtility.successResponseWithMessageAndBody(ResponseConstants.OK,
                    "Transactions fetched", allTransactions.getContent(), HttpStatus.OK);
        } catch (Exception e) {
            log.error("ReserveAndBorrowServiceImpl :: getAllTransactions", e);
            return ResponseUtility.failureResponseWithMessage(ResponseConstants.INTERNAL_ERROR,
                    "Some error occurred", HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    @Override
    public ResponseEntity<Object> searchTransactions(TransactionSearchReq transactionSearchReq) {
        try {
            List<ReserveAndBorrow> reserveAndBorrowList =
                    reserveAndBurrowRepo.searchFilter(transactionSearchReq);
            if (reserveAndBorrowList.isEmpty()) {
                return ResponseUtility.failureResponseWithMessage(ResponseConstants.NOT_FOUND,
                        "No transactions found", HttpStatus.NOT_FOUND);
            }

            return ResponseUtility.successResponseWithMessageAndBody(ResponseConstants.OK,
                    "transactions found",
                    ReserveAndBorrowMapper.mapToBookTransactionDto(reserveAndBorrowList),
                    HttpStatus.OK);
        } catch (Exception e) {
            log.error("ReserveAndBorrowServiceImpl :: searchTransactions", e);
            return ResponseUtility.failureResponseWithMessage(ResponseConstants.INTERNAL_ERROR,
                    "Some error occurred", HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }
}
