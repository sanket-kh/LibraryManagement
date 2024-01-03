package com.librarymanagement.LibraryApplication.controllers;

import com.librarymanagement.LibraryApplication.models.requests.LibrarianAccountRequest;
import com.librarymanagement.LibraryApplication.services.LibrarianAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RestControllerAdvice
@RequiredArgsConstructor
@RequestMapping("/api/v1/librarian/account")
public class LibrarianAccountController {
    private final LibrarianAccountService librarianAccountService;
    @PostMapping("/add")
    public ResponseEntity<Object> addAccountDetails(@RequestBody List<LibrarianAccountRequest> librarianAccountRequest){
        return librarianAccountService.addLibrarianAccount(librarianAccountRequest);
    }
}
