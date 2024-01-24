package com.librarymanagement.LibraryApplication.services;

import com.librarymanagement.LibraryApplication.models.requests.BookSearchFilterRequest;
import com.librarymanagement.LibraryApplication.models.requests.ExistingBookRequest;
import com.librarymanagement.LibraryApplication.models.requests.SaveBookRequest;
import org.springframework.http.ResponseEntity;

public interface BookService {
    ResponseEntity<Object> saveBook(SaveBookRequest saveBookRequest);

    ResponseEntity<Object> getBookByIsbn(Long isbn);

    ResponseEntity<Object> getAllBooksUser(Integer pageNumber);
    ResponseEntity<Object> getAllBooksLibrarian(Integer pageNumber);

    ResponseEntity<Object> updateBookByIsbn(SaveBookRequest saveBookRequest);

    ResponseEntity<Object> addExistingBookByIsbn(ExistingBookRequest existingBookRequest);

    ResponseEntity<Object> searchBookUser(BookSearchFilterRequest bookSearchFilterRequest);
    ResponseEntity<Object> searchBookLibrarian(BookSearchFilterRequest bookSearchFilterRequest);

    ResponseEntity<Object> setBookStatusUnavailable(Long isbn);

    ResponseEntity<Object> setBookStatusAvailable(Long isbn);
}
