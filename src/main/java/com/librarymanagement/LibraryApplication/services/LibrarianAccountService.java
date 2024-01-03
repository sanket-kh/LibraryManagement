package com.librarymanagement.LibraryApplication.services;

import com.librarymanagement.LibraryApplication.models.requests.LibrarianAccountRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface LibrarianAccountService {
    ResponseEntity<Object> addLibrarianAccount(List<LibrarianAccountRequest> librarianAccountRequest);
}
