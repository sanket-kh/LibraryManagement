package com.librarymanagement.LibraryApplication.mappers;

import com.librarymanagement.LibraryApplication.entities.LibrarianAccount;
import com.librarymanagement.LibraryApplication.models.requests.LibrarianAccountRequest;

import java.time.LocalDate;

public class LibrarianAccountMapper {
    public static LibrarianAccount mapToLibrarianAccount(LibrarianAccountRequest request){
        LibrarianAccount librarianAccount = new LibrarianAccount();
        librarianAccount.setActive('A');
        librarianAccount.setAccountNumber(request.getAccountNumber());
        librarianAccount.setAccountName(request.getAccountName());
        librarianAccount.setRecordedDate(LocalDate.now());
        return librarianAccount;
    }
}
