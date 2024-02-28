package com.librarymanagement.LibraryApplication.mappers;

import com.librarymanagement.LibraryApplication.entities.AccountAssociatedOrganization;
import com.librarymanagement.LibraryApplication.entities.AccountType;
import com.librarymanagement.LibraryApplication.entities.LibrarianAccount;
import com.librarymanagement.LibraryApplication.entities.User;
import com.librarymanagement.LibraryApplication.models.dtos.LibrarianAccountDto;
import com.librarymanagement.LibraryApplication.models.requests.LibrarianAccountRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LibrarianAccountMapperInterface {
    @Mapping(target = "librarian", source = "user")
    @Mapping(target = "recordedDate", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "active", expression = "java('Y')")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "modifiedDate", ignore = true)
    LibrarianAccount mapToAddLibrarianAccount(LibrarianAccountRequest request,
                                              AccountType accountType,
                                              AccountAssociatedOrganization accountAssociatedOrganization,
                                              User user);

    @Mapping(target = "librarian", source = "user")
    @Mapping(target = "modifiedDate", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "recordedDate", ignore = true)
    LibrarianAccount mapToModifyLibrarianAccount(LibrarianAccountRequest request,
                                                 AccountType accountType,
                                                 AccountAssociatedOrganization accountAssociatedOrganization,
                                                 User user,
                                                 @MappingTarget LibrarianAccount librarianAccount);

    @Mapping(target = "accountTypeName", source = "librarianAccount.accountType.accountTypeName")
    @Mapping(target = "accountAssociatedOrganizationName",
            source = "librarianAccount.accountAssociatedOrganization.organizationName")
    LibrarianAccountDto mapToLibrarianAccountDto(LibrarianAccount librarianAccount);

    List<LibrarianAccountDto> mapToLibrarianAccountDto(List<LibrarianAccount> librarianAccounts);
}
