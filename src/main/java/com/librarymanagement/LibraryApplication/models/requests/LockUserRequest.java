package com.librarymanagement.LibraryApplication.models.requests;

import com.librarymanagement.LibraryApplication.utils.RegexConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LockUserRequest {
    @NotBlank(message ="username is null or empty")
    @Pattern(regexp = RegexConstants.USERNAME_REGEX , message = "Invalid username format")
    private String username;

    private String remark;

}
