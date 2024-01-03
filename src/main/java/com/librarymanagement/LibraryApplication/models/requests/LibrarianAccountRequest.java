package com.librarymanagement.LibraryApplication.models.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LibrarianAccountRequest {
    private String accountTypeName;
    private String accountAssociatedOrganizationName;
    private String accountName;
    private Long accountNumber;
}
