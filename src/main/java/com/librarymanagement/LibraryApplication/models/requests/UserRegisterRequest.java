package com.librarymanagement.LibraryApplication.models.requests;

import com.librarymanagement.LibraryApplication.utils.RegexConstants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterRequest {
    @Pattern(regexp = RegexConstants.TITLE_CASE_REGEX, message ="Name should be in title case")
    private String firstName;

    @Pattern(regexp = RegexConstants.TITLE_CASE_REGEX, message ="Name should be in title case")
    private String lastName;

    @NotBlank(message = "username is empty")
    @Pattern(regexp = RegexConstants.USERNAME_REGEX , message = "Invalid username format")
    private String username;

    @Email(message = "Invalid email format")
    private String email;

    @Pattern(regexp = RegexConstants.PHONE_REGEX, message = "Invalid phone number format")
    private Long phone;

    @Pattern(regexp = RegexConstants.ADDRESS_REGEX, message = "Invalid address format")
    private String address;

    @NotBlank(message = "password is empty")
    @Pattern(regexp = RegexConstants.PASSWORD_REGEX, message = "Password doesnt match password " +
                                                               "policy")
    private String password;
}
