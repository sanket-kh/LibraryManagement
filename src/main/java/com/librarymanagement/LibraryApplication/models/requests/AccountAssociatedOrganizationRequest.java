package com.librarymanagement.LibraryApplication.models.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountAssociatedOrganizationRequest {
    @NotBlank(message = "Account Type cannot be null or empty")
    private String accountTypeName;

    @NotBlank(message = "Account Associated Organization cannot be null or empty")
    private String accountAssociatedOrganizationName;
}
