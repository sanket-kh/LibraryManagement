package com.librarymanagement.LibraryApplication.mappers;

import com.librarymanagement.LibraryApplication.entities.LibrarianAccount;
import com.librarymanagement.LibraryApplication.models.dtos.LibrarianAccountDto;
import com.librarymanagement.LibraryApplication.models.requests.LibrarianAccountRequest;
import org.hibernate.boot.model.naming.IllegalIdentifierException;

import java.time.LocalDate;
import java.util.List;

public class LibrarianAccountMapper {
    public static LibrarianAccount mapToLibrarianAccount(LibrarianAccountRequest request){
        LibrarianAccount librarianAccount = new LibrarianAccount();
        librarianAccount.setActive('Y');
        librarianAccount.setAccountNumber(request.getAccountNumber());
        librarianAccount.setAccountName(request.getAccountName());
        librarianAccount.setRecordedDate(LocalDate.now());
        return librarianAccount;
    }
    public static LibrarianAccount mapToModifyLibrarianAccount(LibrarianAccountRequest request){
        LibrarianAccount librarianAccount = new LibrarianAccount();
        librarianAccount.setActive('A');
        librarianAccount.setAccountNumber(request.getAccountNumber());
        librarianAccount.setAccountName(request.getAccountName());
        librarianAccount.setModifiedDate(LocalDate.now());
        return librarianAccount;
    }
    public static LibrarianAccountDto mapToLibrarianAccountDto(LibrarianAccount librarianAccount){
        LibrarianAccountDto librarianAccountDto = new LibrarianAccountDto();
        librarianAccountDto.setAccountTypeName(librarianAccount.getAccountType().getAccountTypeName());
        librarianAccountDto.setAccountAssociatedOrganizationName(librarianAccount.getAccountAssociatedOrganization().getOrganizationName());
        librarianAccountDto.setAccountName(librarianAccount.getAccountName());
        librarianAccountDto.setAccountNumber(librarianAccount.getAccountNumber());
        librarianAccountDto.setRecordedDate(librarianAccount.getRecordedDate());
        librarianAccountDto.setModifiedDate(librarianAccount.getModifiedDate());
        return librarianAccountDto;
    }
    public static List<LibrarianAccountDto> mapToLibrarianAccountDto(List<LibrarianAccount> librarianAccounts){
        return librarianAccounts.stream().map(LibrarianAccountMapper::mapToLibrarianAccountDto).toList();
    }

}
