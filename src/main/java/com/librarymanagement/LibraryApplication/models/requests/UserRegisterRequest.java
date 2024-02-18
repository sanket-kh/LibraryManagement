package com.librarymanagement.LibraryApplication.models.requests;

import com.librarymanagement.LibraryApplication.utils.RegexConstants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

@Getter
@Setter
public class UserRegisterRequest {
    @Pattern(regexp = RegexConstants.TITLE_CASE_REGEX, message ="Name should be in title case")
    private String firstName;

    @Pattern(regexp = RegexConstants.TITLE_CASE_REGEX, message ="Name should be in title case")
    private String lastName;

    @NotBlank(message = "username is empty")
    @Size(min = 3, max = 20 , message = "username should be between 3 to 20 characters")
    @Pattern(regexp = RegexConstants.USERNAME_REGEX , message = "Invalid username format")
    private String username;

    @Email(message = "Invalid email format")
    private String email;

    @Range(min = 9600000000L , max=9899999999L, message = "Invalid phone number format")
    private Long phone;

    @Pattern(regexp = RegexConstants.ADDRESS_REGEX, message = "Invalid address format")
    private String address;

    @NotBlank(message = "password is empty")
    @Pattern(regexp = RegexConstants.PASSWORD_REGEX, message = "Password doesnt match password " +
                                                               "policy")
    private String password;
}
