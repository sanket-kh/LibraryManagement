package com.librarymanagement.LibraryApplication.models.requests;

import com.librarymanagement.LibraryApplication.utils.RegexConstants;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BorrowRequest {
    @NotBlank(message ="username is null or empty")
    @Pattern(regexp = RegexConstants.USERNAME_REGEX , message = "Invalid username format")
    private String username;

    @Min(value = 3L , message="ISBN should be min 3 digits")
    @Max(value =13L , message="ISBN should be max 13 digits")
    private Long isbn;

}
