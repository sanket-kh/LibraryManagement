package com.librarymanagement.LibraryApplication.controllers;


import com.librarymanagement.LibraryApplication.models.dtos.BookDto;
import com.librarymanagement.LibraryApplication.models.requests.BookSearchFilterRequest;
import com.librarymanagement.LibraryApplication.models.requests.ExistingBookRequest;
import com.librarymanagement.LibraryApplication.models.requests.SaveBookRequest;
import com.librarymanagement.LibraryApplication.models.responses.DefaultResponse;
import com.librarymanagement.LibraryApplication.services.BookService;
import com.librarymanagement.LibraryApplication.utils.ResponseConstants;
import com.librarymanagement.LibraryApplication.utils.ResponseUtility;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RestControllerAdvice
@RequiredArgsConstructor
@RequestMapping(value = "api/v1/books")
public class BookController {
    private final BookService bookService;

    @PostMapping(value = "/save")
    public ResponseEntity<Object> saveBook(@RequestBody SaveBookRequest saveBookRequest) {
        try {
            return bookService.saveBook(saveBookRequest);
        } catch (Exception e) {
            log.error("BookController :: saveBook", e);
            return   ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR,
                    "Server Error", HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @GetMapping("/user/{isbn}")
    public ResponseEntity<Object> getBookByIsbn(@PathVariable Long isbn) {
        try {
            return bookService.getBookByIsbn(isbn);
        } catch (Exception e) {
            log.error("BookController :: getBookByIsbn", e);
            return   ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR,
                    "Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user/get-all")
    public ResponseEntity<Object> getAllBooksUser(@RequestParam(defaultValue = "0") Integer page) {
        try {
            return bookService.getAllBooksUser(page);
        } catch (Exception e) {
            log.error("BookController :: getAllBooks", e);
            return   ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR,
                    "Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/admin/get-all")
    @Operation(summary = "get a list of associated organization by type",
            responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation",
                            content=@Content(mediaType="application/json",
                                    array = @ArraySchema(schema = @Schema(implementation =
                                            BookDto.class)))),
                    @ApiResponse(responseCode = ResponseConstants.SERVER_ERROR, description = "Internal Server Error",
                            content=@Content(mediaType = "application/json",
                                    schema =@Schema(implementation = DefaultResponse.class))),
                    @ApiResponse(responseCode = ResponseConstants.NOT_FOUND, description = "No " +
                                                                                           "organization " +
                                                                                           "found for " +
                                                                                           "account type",
                            content=@Content(mediaType = "application/json",
                                    schema =@Schema(implementation = DefaultResponse.class))),

            })
    public ResponseEntity<Object> getAllBooksLibrarian(@RequestParam(defaultValue = "0") Integer page) {
        try {
            return bookService.getAllBooksLibrarian(page);
        } catch (Exception e) {
            log.error("BookController :: getAllBooks", e);
            return   ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR,
                    "Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/update")
    public ResponseEntity<Object> updateBookById(@RequestBody SaveBookRequest saveBookRequest) {
        try {
            return bookService.updateBookByIsbn(saveBookRequest);
        } catch (Exception e) {
            log.error("BookController :: updateBookById", e);
            return   ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR,
                    "Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add-existing")
    public ResponseEntity<Object> addExistingBookByIsbn(@RequestBody ExistingBookRequest existingBookRequest) {
        try {
            return bookService.addExistingBookByIsbn(existingBookRequest);
        } catch (Exception e) {
            log.error("BookController :: addExistingBookByIsbn", e);
            return   ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR,
                    "Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/user/search")
    public ResponseEntity<Object> searchBookUser(@Valid @RequestBody BookSearchFilterRequest bookSearchFilterRequest) {
        try {
            if ((bookSearchFilterRequest.getTitle() == null || bookSearchFilterRequest.getTitle().isBlank()) && bookSearchFilterRequest.getIsbn() == null && (bookSearchFilterRequest.getAuthor() == null || bookSearchFilterRequest.getAuthor().isBlank())) {
                return   ResponseUtility.failureResponseWithMessage(ResponseConstants.BAD_REQUEST,
                        "Invalid search parameters", HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return bookService.searchBookUser(bookSearchFilterRequest);
        } catch (Exception e) {
            log.error("BookController :: searchBook", e);
            return   ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR,
                    "Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/admin/search")
    public ResponseEntity<Object> searchBookLibrarian(@Valid @RequestBody BookSearchFilterRequest bookSearchFilterRequest) {
        try {
            if ((bookSearchFilterRequest.getTitle() == null || bookSearchFilterRequest.getTitle().isBlank()) && bookSearchFilterRequest.getIsbn() == null && (bookSearchFilterRequest.getAuthor() == null || bookSearchFilterRequest.getAuthor().isBlank())) {
                return   ResponseUtility.failureResponseWithMessage(ResponseConstants.BAD_REQUEST,
                        "Invalid search parameters", HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return bookService.searchBookLibrarian(bookSearchFilterRequest);
        } catch (Exception e) {
            log.error("BookController :: searchBook", e);
            return   ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR,
                    "Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/availability/unavailable")
    public ResponseEntity<Object> setBookUnavailable(@RequestParam Long isbn) {
        try {
            return bookService.setBookStatusUnavailable(isbn);
        } catch (Exception e) {
            log.error("BookController :: setBookUnavailable", e);
            return   ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR,
                    "Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/availability/available")
    public ResponseEntity<Object> setBookAvailable(@RequestParam Long isbn) {
        try {
            return bookService.setBookStatusAvailable(isbn);
        } catch (Exception e) {
            log.error("BookController :: setBookAvailable", e);
            return   ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR,
                    "Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
