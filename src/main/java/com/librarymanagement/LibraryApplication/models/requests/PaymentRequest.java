package com.librarymanagement.LibraryApplication.models.requests;

import com.librarymanagement.LibraryApplication.utils.RegexConstants;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentRequest {
    @NotBlank(message ="username is null or empty")
    @Pattern(regexp = RegexConstants.USERNAME_REGEX , message = "Invalid username format")
    private String username;

    @Min(value = 3L , message="ISBN should be min 3 digits")
    @Max(value =13L , message="ISBN should be max 13 digits")
    private Long isbn;

    @NotNull(message = "amount cannot be null or empty")
    private Integer amount;
}
