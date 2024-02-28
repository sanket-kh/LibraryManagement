package com.librarymanagement.LibraryApplication.mappers.deprecatedmappers;

import com.librarymanagement.LibraryApplication.entities.AccountAssociatedOrganization;
import com.librarymanagement.LibraryApplication.entities.AccountType;
import com.librarymanagement.LibraryApplication.entities.LibrarianAccount;
import com.librarymanagement.LibraryApplication.entities.User;
import com.librarymanagement.LibraryApplication.models.dtos.LibrarianAccountDto;
import com.librarymanagement.LibraryApplication.models.requests.LibrarianAccountRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
/**
 * @deprecated since 2/26/2024. Replaced by the mapper interfaces and MapStruct library
 */
@Deprecated(since = "2/26/2024")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LibrarianAccountMapper {
    public static LibrarianAccount mapToAddLibrarianAccount(LibrarianAccountRequest request,
                                                            AccountType accountType,
                                                            AccountAssociatedOrganization accountAssociatedOrganization,
                                                            User user) {
        LibrarianAccount librarianAccount = new LibrarianAccount();
        librarianAccount.setActive('Y');
        librarianAccount.setAccountNumber(request.getAccountNumber());
        librarianAccount.setAccountName(request.getAccountName());
        librarianAccount.setRecordedDate(LocalDateTime.now());
        librarianAccount.setAccountType(accountType);
        librarianAccount.setAccountAssociatedOrganization(accountAssociatedOrganization);
        librarianAccount.setLibrarian(user);
        return librarianAccount;
    }
    public static LibrarianAccount mapToModifyLibrarianAccount(LibrarianAccountRequest request,
                                                               AccountType accountType,
                                                               AccountAssociatedOrganization accountAssociatedOrganization,
                                                               User user, LibrarianAccount librarianAccount) {

        librarianAccount.setActive('Y');
        librarianAccount.setAccountNumber(request.getAccountNumber());
        librarianAccount.setAccountName(request.getAccountName());
        librarianAccount.setModifiedDate(LocalDateTime.now());
        librarianAccount.setAccountType(accountType);
        librarianAccount.setAccountAssociatedOrganization(accountAssociatedOrganization);
        librarianAccount.setLibrarian(user);
        return librarianAccount;
    }

    public static LibrarianAccountDto mapToLibrarianAccountDto(LibrarianAccount librarianAccount) {
        LibrarianAccountDto librarianAccountDto = new LibrarianAccountDto();
        librarianAccountDto.setAccountTypeName(librarianAccount.getAccountType().getAccountTypeName());
        librarianAccountDto.setAccountAssociatedOrganizationName(librarianAccount.getAccountAssociatedOrganization().getOrganizationName());
        librarianAccountDto.setAccountName(librarianAccount.getAccountName());
        librarianAccountDto.setAccountNumber(librarianAccount.getAccountNumber());
        librarianAccountDto.setRecordedDate(librarianAccount.getRecordedDate());
        librarianAccountDto.setModifiedDate(librarianAccount.getModifiedDate());
        return librarianAccountDto;
    }

    public static List<LibrarianAccountDto> mapToLibrarianAccountDto(List<LibrarianAccount> librarianAccounts) {
        return librarianAccounts.stream().map(LibrarianAccountMapper::mapToLibrarianAccountDto).toList();
    }


}
