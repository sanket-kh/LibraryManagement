package com.librarymanagement.LibraryApplication.services.serviceImpls;

import com.librarymanagement.LibraryApplication.entities.Book;
import com.librarymanagement.LibraryApplication.mappers.BookMapper;
import com.librarymanagement.LibraryApplication.models.dtos.bookdtos.BookDto;
import com.librarymanagement.LibraryApplication.models.dtos.bookdtos.UserBookDto;
import com.librarymanagement.LibraryApplication.models.requests.BookSearchFilterRequest;
import com.librarymanagement.LibraryApplication.models.requests.ExistingBookRequest;
import com.librarymanagement.LibraryApplication.models.requests.SaveBookRequest;
import com.librarymanagement.LibraryApplication.repositories.BookRepo;
import com.librarymanagement.LibraryApplication.services.BookService;
import com.librarymanagement.LibraryApplication.utils.ResponseConstants;
import com.librarymanagement.LibraryApplication.utils.ResponseUtility;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class BookServiceImpl implements BookService {
    private BookRepo bookRepo;

    @Override
    public ResponseEntity<Object> saveBook(SaveBookRequest saveBookRequest) {

        try {

            Book existingBook = bookRepo.getBookByIsbn(saveBookRequest.getIsbn());
            if (existingBook != null) {
                return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.ALREADY_EXISTS,
                        "Book record already exists, please add to existing record"), HttpStatus.OK);
            }

            Book book = BookMapper.mapSaveBookRequestToBook(saveBookRequest);
            bookRepo.save(book);
            return new ResponseEntity<>(ResponseUtility.successResponseWithMessage(ResponseConstants.CREATED,
                    "Book saved"), HttpStatus.OK);

        } catch (Exception e) {
            log.error("BookServiceImpl :: saveBook ", e);
            return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.INTERNAL_ERROR,
                    "Unable to save book"), HttpStatus.OK);

        }
    }

    @Override
    public ResponseEntity<Object> getBookByIsbn(Long isbn) {
        try {
            Book book = bookRepo.getBookByIsbn(isbn);
            if (book == null) {
                return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.NOT_FOUND,
                        "Book not found"), HttpStatus.OK);
            }
            UserBookDto userBookDto = mapBookToUserBookDto(book);
            return new ResponseEntity<>(ResponseUtility.successResponseWithMessageAndBody(ResponseConstants.OK,
                    "Book found", userBookDto), HttpStatus.OK);
        } catch (Exception e) {
            log.error("BookService :: getBookByIsbn", e);
            return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.INTERNAL_ERROR,
                    "Unable to fetch book"), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<Object> getAllBooks(Integer pageNumber) {
        try {
            Pageable pageable = PageRequest.of(pageNumber, 1, Sort.by("title"));
            Page<Book> bookPage = bookRepo.findAll(pageable);
            List<Book> bookList = bookPage.getContent();
            if (bookList.isEmpty()) {
                return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.NOT_FOUND,
                        "No books in the library"), HttpStatus.OK);
            }

            return new ResponseEntity<>(ResponseUtility.successResponseWithMessageAndBody(ResponseConstants.OK,
                    "Books found", BookMapper.mapBookListToBaseBookDtoList(bookList)), HttpStatus.OK);

        } catch (Exception e) {
            log.error("BookService :: getAllBooks", e);
            return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.INTERNAL_ERROR,
                    "Unable to fetch books"), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<Object> updateBookById(Long id, SaveBookRequest saveBookRequest) {
        try {
            Book book = bookRepo.getBookById(id);
            if (book == null) {
                return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.NOT_FOUND,
                        "Book doesnt exist with id:" + id), HttpStatus.OK);
            }
            book.setTitle(saveBookRequest.getTitle());
            book.setCopies(saveBookRequest.getCopies());
            book.setIsbn(saveBookRequest.getIsbn());
            book.setAuthor(saveBookRequest.getAuthor());
            bookRepo.save(book);
            book = bookRepo.getBookById(id);
            SaveBookRequest updatedBookDto = BookMapper.mapBookToSaveBookRequest(book);
            return new ResponseEntity<>(ResponseUtility.successResponseWithMessageAndBody(ResponseConstants.UPDATED,
                    "Book updated", updatedBookDto), HttpStatus.OK);
        } catch (Exception e) {
            log.error("BookService :: updateBookById", e);
            return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.INTERNAL_ERROR,
                    "Failed to update book"), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<Object> addExistingBookByIsbn(ExistingBookRequest existingBookRequest) {
        try {
            Book book = bookRepo.getBookByIsbn(existingBookRequest.getIsbn());
            if (book == null) {
                return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.NOT_FOUND,
                        "Book with isbn:" + existingBookRequest.getIsbn() + " doesnt exist"), HttpStatus.OK);
            }
            book.setCopies(book.getCopies() + existingBookRequest.getCopies());
            bookRepo.save(book);
            return new ResponseEntity<>(ResponseUtility.successResponseWithMessage(ResponseConstants.UPDATED,
                    "Quantity of books updated"), HttpStatus.OK);
        } catch (Exception e) {
            log.error("BookService :: addExistingBookByIsbn", e);
            return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.INTERNAL_ERROR,
                    "Unable update quantity of book"), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<Object> searchBook(BookSearchFilterRequest bookSearchFilterRequest) {
        try {
            List<Book> bookList = bookRepo.bookSearchFilter(bookSearchFilterRequest);
            System.out.println(bookList.size());
            if (bookList.isEmpty()) {
                return new ResponseEntity<>(ResponseUtility.successResponseWithMessage(ResponseConstants.OK,
                        "Book(s) not found, try again"), HttpStatus.OK);

            }
            List<BookDto> bookDtoList = BookMapper.mapBookListToBaseBookDtoList(bookList);
            return new ResponseEntity<>(ResponseUtility.successResponseWithMessageAndBody(ResponseConstants.OK,
                    "Book(s) found", bookDtoList), HttpStatus.OK);
        } catch (Exception e) {
            log.error("BookService :: searchBook", e);
            return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.INTERNAL_ERROR,
                    "Unable to search book"), HttpStatus.OK);
        }

    }

    @Override
    public ResponseEntity<Object> setBookStatusUnavailable(Long isbn) {

        try {
            Book book = bookRepo.getBookByIsbn(isbn);
            if (book == null) {
                return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.NOT_FOUND,
                        "Book with isbn: " + isbn + " doesn't exist"), HttpStatus.OK);
            }
            book.setIsAvailable(Boolean.FALSE);
            bookRepo.save(book);
            return new ResponseEntity<>(ResponseUtility.successResponseWithMessage(ResponseConstants.OK,
                    "Book availability updated to unavailable"), HttpStatus.OK);
        } catch (Exception e) {
            log.error("BookService :: setBookStatusUnavailable", e);
            return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.INTERNAL_ERROR,
                    "Unable to update book's status"), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<Object> setBookStatusAvailable(Long isbn) {

        try {
            Book book = bookRepo.getBookByIsbn(isbn);
            if (book == null) {
                return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.NOT_FOUND,
                        "Book with isbn: " + isbn + " doesn't exist"), HttpStatus.OK);
            }
            book.setIsAvailable(Boolean.TRUE);
            bookRepo.save(book);
            return new ResponseEntity<>(ResponseUtility.successResponseWithMessage(ResponseConstants.OK,
                    "Book with isbn: " + isbn + " availability updated to available"), HttpStatus.OK);
        } catch (Exception e) {
            log.error("BookService :: setBookStatusAvailable", e);
            return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.INTERNAL_ERROR,
                    "Unable to update book's status"), HttpStatus.OK);
        }
    }


}
