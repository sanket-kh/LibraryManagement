package com.librarymanagement.LibraryApplication.models.requests;

import com.librarymanagement.LibraryApplication.utils.RegexConstants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

@Getter
@Setter
public class ChangeUserDetailsReq {
    @Pattern(regexp = RegexConstants.TITLE_CASE_REGEX, message ="Name should be in title case")
    private String firstName;

    @Pattern(regexp = RegexConstants.TITLE_CASE_REGEX, message ="Name should be in title case")
    private String lastName;

    @Email(message = "Invalid email format")
    private String email;

    @Range(min = 9600000000L , max=9899999999L, message = "Invalid phone number format")
    private Long phone;

    @Pattern(regexp = RegexConstants.ADDRESS_REGEX, message = "Invalid address format")
    private String address;
}
