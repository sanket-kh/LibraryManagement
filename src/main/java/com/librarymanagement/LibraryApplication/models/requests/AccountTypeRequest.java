package com.librarymanagement.LibraryApplication.models.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountTypeRequest {
    @NotBlank(message = "Account type cannot be null or empty")
    private String accountTypeName;
}
