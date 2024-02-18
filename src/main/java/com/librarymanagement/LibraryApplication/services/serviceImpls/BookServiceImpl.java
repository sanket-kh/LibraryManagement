package com.librarymanagement.LibraryApplication.services.serviceImpls;

import com.librarymanagement.LibraryApplication.entities.Book;
import com.librarymanagement.LibraryApplication.mappers.BookMapper;
import com.librarymanagement.LibraryApplication.models.dtos.BookDto;
import com.librarymanagement.LibraryApplication.models.dtos.UserBookDto;
import com.librarymanagement.LibraryApplication.models.requests.BookSearchFilterRequest;
import com.librarymanagement.LibraryApplication.models.requests.ExistingBookRequest;
import com.librarymanagement.LibraryApplication.models.requests.SaveBookRequest;
import com.librarymanagement.LibraryApplication.repositories.BookRepo;
import com.librarymanagement.LibraryApplication.repositories.ReserveAndBurrowRepo;
import com.librarymanagement.LibraryApplication.services.BookService;
import com.librarymanagement.LibraryApplication.utils.Constants;
import com.librarymanagement.LibraryApplication.utils.ResponseConstants;
import com.librarymanagement.LibraryApplication.utils.ResponseUtility;
import com.librarymanagement.LibraryApplication.utils.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.librarymanagement.LibraryApplication.mappers.BookMapper.mapBookToUserBookDto;


@Log4j2
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepo bookRepo;
    private final ReserveAndBurrowRepo reserveAndBurrowRepo;

    @Override
    public ResponseEntity<Object> saveBook(SaveBookRequest saveBookRequest) {

        try {
            Book existingBook = bookRepo.getBookByIsbn(saveBookRequest.getIsbn());
            if (existingBook != null) {
                return ResponseUtility.failureResponseWithMessage(ResponseConstants.ALREADY_EXISTS,
                        "Book record already exists, please add to existing record",
                        HttpStatus.CONFLICT);
            }

            Book book = BookMapper.mapSaveBookRequestToBook(saveBookRequest);
            bookRepo.save(book);
            return ResponseUtility.successResponseWithMessage(ResponseConstants.CREATED,
                    "Book saved", HttpStatus.OK);

        } catch (Exception e) {
            log.error("BookServiceImpl :: saveBook ", e);
            return ResponseUtility.failureResponseWithMessage(ResponseConstants.INTERNAL_ERROR,
                    "Unable to save book", HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @Override
    public ResponseEntity<Object> getBookByIsbn(Long isbn) {
        try {
            Book book = bookRepo.getBookByIsbn(isbn);
            if (book == null) {
                return ResponseUtility.failureResponseWithMessage(ResponseConstants.NOT_FOUND,
                        "Book not found", HttpStatus.CONFLICT);
            }
            UserBookDto userBookDto = mapBookToUserBookDto(book);
            return ResponseUtility.successResponseWithMessageAndBody(ResponseConstants.OK,
                    "Book found", userBookDto, HttpStatus.OK);
        } catch (Exception e) {
            log.error("BookService :: getBookByIsbn", e);
            return ResponseUtility.failureResponseWithMessage(ResponseConstants.INTERNAL_ERROR,
                    "Unable to fetch book", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Object> getAllBooksUser(Integer pageNumber) {
        try {
            Pageable pageable = PageRequest.of(pageNumber,Constants.PAGE_SIZE, Sort.by("title"));
            Page<Book> bookPage = bookRepo.getAvailableBooks(pageable);
            List<Book> bookList = bookPage.getContent();
            if (bookList.isEmpty()) {
                return ResponseUtility.failureResponseWithMessage(ResponseConstants.NOT_FOUND,
                        "No books in the library", HttpStatus.NOT_FOUND);
            }
            List<UserBookDto> userBookDtos = BookMapper.mapBookListToUserBookDtoList(bookList);
            return ResponseUtility.successResponseWithMessageAndBody(ResponseConstants.OK,
                    "Books found", userBookDtos, HttpStatus.OK);

        } catch (Exception e) {
            log.error("BookService :: getAllBooks", e);
            return ResponseUtility.failureResponseWithMessage(ResponseConstants.INTERNAL_ERROR,
                    "Unable to fetch books", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Object> getAllBooksLibrarian(Integer pageNumber) {
        try {
            Pageable pageable = PageRequest.of(pageNumber, Constants.PAGE_SIZE, Sort.by("title"));
            Page<Book> bookPage = bookRepo.findAll(pageable);
            List<Book> bookList = bookPage.getContent();
            if (bookList.isEmpty()) {
                return ResponseUtility.failureResponseWithMessage(ResponseConstants.NOT_FOUND,
                        "No books in the library", HttpStatus.NOT_FOUND);
            }
            List<BookDto> bookDtos = BookMapper.mapBookListToBaseBookDtoList(bookList);
            return ResponseUtility.successResponseWithMessageAndBody(ResponseConstants.OK,
                    "Books found",bookDtos , HttpStatus.OK);

        } catch (Exception e) {
            log.error("BookService :: getAllBooks", e);
            return ResponseUtility.failureResponseWithMessage(ResponseConstants.INTERNAL_ERROR,
                    "Unable to fetch books",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Object> updateBookByIsbn(SaveBookRequest saveBookRequest) {
        try {
            Book book = bookRepo.getBookByIsbn(saveBookRequest.getIsbn());
            if (book == null) {
                return ResponseUtility.failureResponseWithMessage(ResponseConstants.NOT_FOUND,
                        "Book doesnt exist with isbn:" + saveBookRequest.getIsbn(),
                HttpStatus.NOT_FOUND);
            }
            book = BookMapper.mapUpdateBookRequestToBook(saveBookRequest,book);
            bookRepo.save(book);
            return ResponseUtility.successResponseWithMessage(ResponseConstants.UPDATED,
                    "Book updated", HttpStatus.OK);
        } catch (Exception e) {
            log.error("BookService :: updateBookByIsbn", e);
            return ResponseUtility.failureResponseWithMessage(ResponseConstants.INTERNAL_ERROR,
                    "Failed to update book", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Object> addExistingBookByIsbn(ExistingBookRequest existingBookRequest) {
        try {
            Book book = bookRepo.getBookByIsbn(existingBookRequest.getIsbn());
            if (book == null) {
                return ResponseUtility.failureResponseWithMessage(ResponseConstants.NOT_FOUND,
                        "Book with isbn:" + existingBookRequest.getIsbn() + " doesnt exist",
                HttpStatus.NOT_FOUND);
            }
            book.setCopies(book.getCopies() + existingBookRequest.getCopies());
            bookRepo.save(book);
            return ResponseUtility.successResponseWithMessage(ResponseConstants.UPDATED,
                    "Quantity of books updated",HttpStatus.OK);
        } catch (Exception e) {
            log.error("BookService :: addExistingBookByIsbn", e);
            return ResponseUtility.failureResponseWithMessage(ResponseConstants.INTERNAL_ERROR,
                    "Unable update quantity of book",HttpStatus.INTERNAL_SERVER_ERROR     );
        }
    }


    @Override
    public ResponseEntity<Object> searchBookUser(BookSearchFilterRequest bookSearchFilterRequest) {
        try {
            List<Book> bookList = bookRepo.bookSearchFilter(bookSearchFilterRequest);
            System.out.println(bookList.size());
            if (bookList.isEmpty()) {
                return ResponseUtility.successResponseWithMessage(ResponseConstants.OK,
                        "Book(s) not found, try again",HttpStatus.NOT_FOUND);

            }
            List<UserBookDto> bookDtoList = BookMapper.mapBookListToUserBookDtoList(bookList);
            return ResponseUtility.successResponseWithMessageAndBody(ResponseConstants.OK,
                    "Book(s) found", bookDtoList,HttpStatus.OK);
        } catch (Exception e) {
            log.error("BookService :: searchBook", e);
            return ResponseUtility.failureResponseWithMessage(ResponseConstants.INTERNAL_ERROR,
                    "Unable to search book",HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public ResponseEntity<Object> searchBookLibrarian(BookSearchFilterRequest bookSearchFilterRequest) {
        try {
            List<Book> bookList = bookRepo.bookSearchFilter(bookSearchFilterRequest);
            System.out.println(bookList.size());
            if (bookList.isEmpty()) {
                return ResponseUtility.successResponseWithMessage(ResponseConstants.OK,
                        "Book(s) not found, try again",HttpStatus.NOT_FOUND);
            }
            List<BookDto> bookDtoList = BookMapper.mapBookListToBaseBookDtoList(bookList);
            return ResponseUtility.successResponseWithMessageAndBody(ResponseConstants.OK,
                    "Book(s) found", bookDtoList,HttpStatus.OK);
        } catch (Exception e) {
            log.error("BookService :: searchBook", e);
            return ResponseUtility.failureResponseWithMessage(ResponseConstants.INTERNAL_ERROR,
                    "Unable to search book",HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public ResponseEntity<Object> setBookStatusUnavailable(Long isbn) {

        try {
            Book book = bookRepo.getBookByIsbn(isbn);
            if (book == null) {
                return ResponseUtility.failureResponseWithMessage(ResponseConstants.NOT_FOUND,
                        "Book with isbn: " + isbn + " doesn't exist",HttpStatus.NOT_FOUND);
            }
            book.setIsAvailable(Boolean.FALSE);
            bookRepo.save(book);
            return ResponseUtility.successResponseWithMessage(ResponseConstants.OK,
                    "Book availability updated to unavailable",HttpStatus.OK);
        } catch (Exception e) {
            log.error("BookService :: setBookStatusUnavailable", e);
            return ResponseUtility.failureResponseWithMessage(ResponseConstants.INTERNAL_ERROR,
                    "Unable to update book's status",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Object> setBookStatusAvailable(Long isbn) {

        try {
            Book book = bookRepo.getBookByIsbn(isbn);
            if (book == null) {
                return ResponseUtility.failureResponseWithMessage(ResponseConstants.NOT_FOUND,
                        "Book with isbn: " + isbn + " doesn't exist",HttpStatus.NOT_FOUND);
            }
            book.setIsAvailable(Boolean.TRUE);
            bookRepo.save(book);
            return ResponseUtility.successResponseWithMessage(ResponseConstants.OK,
                    "Book with isbn: " + isbn + " availability updated to available",HttpStatus.OK)
            ;
        } catch (Exception e) {
            log.error("BookService :: setBookStatusAvailable", e);
            return ResponseUtility.failureResponseWithMessage(ResponseConstants.INTERNAL_ERROR,
                    "Unable to update book's status", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Object> getBorrowedBooksUser() {
        String username = Utils.getUsernameFromContext();
        List<UserBookDto> booksBorrowedByUser = this.reserveAndBurrowRepo.getBooksBorrowedByUser(username);
        if(booksBorrowedByUser.isEmpty()){
            return ResponseUtility.failureResponseWithMessage(ResponseConstants.NOT_FOUND,
                    "You have not borrowed any books",HttpStatus.NOT_FOUND);
        }
        return ResponseUtility.successResponseWithMessageAndBody(ResponseConstants.OK,
                "Books borrowed by user retrieved", booksBorrowedByUser, HttpStatus.OK);
    }


}
