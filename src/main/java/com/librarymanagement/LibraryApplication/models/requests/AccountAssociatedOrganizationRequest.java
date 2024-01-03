package com.librarymanagement.LibraryApplication.models.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountAssociatedOrganizationRequest {
    private String accountTypeName;
    private String accountAssociatedOrganizationName;
}
