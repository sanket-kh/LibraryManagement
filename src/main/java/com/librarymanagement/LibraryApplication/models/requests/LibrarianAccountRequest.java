package com.librarymanagement.LibraryApplication.models.requests;

import com.librarymanagement.LibraryApplication.utils.RegexConstants;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class LibrarianAccountRequest {
    @Pattern(regexp = RegexConstants.TITLE_CASE_REGEX, message = "Account type name should be in " +
                                                                 "title case")
    private String accountTypeName;

    @Pattern(regexp = RegexConstants.TITLE_CASE_REGEX, message = "Organization name should be in " +
                                                                 "title case")
    private String accountAssociatedOrganizationName;

    @Pattern(regexp = RegexConstants.TITLE_CASE_REGEX, message = "Name should be in title case")
    private String accountName;

    @Pattern(regexp = RegexConstants.ACCOUNT_NUMBER_REGEX, message = "Invalid account number")
    private Long accountNumber;
}
