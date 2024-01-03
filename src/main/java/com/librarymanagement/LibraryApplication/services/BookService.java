package com.librarymanagement.LibraryApplication.services;

import com.librarymanagement.LibraryApplication.models.requests.BookSearchFilterRequest;
import com.librarymanagement.LibraryApplication.models.requests.ExistingBookRequest;
import com.librarymanagement.LibraryApplication.models.requests.SaveBookRequest;
import org.springframework.http.ResponseEntity;

public interface BookService {
    ResponseEntity<Object> saveBook(SaveBookRequest saveBookRequest);

    ResponseEntity<Object> getBookByIsbn(Long isbn);

    ResponseEntity<Object> getAllBooks(Integer pageNumber);

    ResponseEntity<Object> updateBookByIsbn(SaveBookRequest saveBookRequest);

    ResponseEntity<Object> addExistingBookByIsbn(ExistingBookRequest existingBookRequest);

    ResponseEntity<Object> searchBook(BookSearchFilterRequest bookSearchFilterRequest);

    ResponseEntity<Object> setBookStatusUnavailable(Long isbn);

    ResponseEntity<Object> setBookStatusAvailable(Long isbn);
}
