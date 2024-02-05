package com.librarymanagement.LibraryApplication.models.requests;

import com.librarymanagement.LibraryApplication.utils.RegexConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordRequest {
    @NotBlank(message = "Username cannot be null or empty")
    private String username;

    @Pattern(regexp = RegexConstants.PASSWORD_REGEX, message =
            "Password doesnt match password policy")
    private String currentPassword;

    @Pattern(regexp = RegexConstants.PASSWORD_REGEX, message =
            "Password doesnt match password policy")
    private String newPassword;

    @Pattern(regexp = RegexConstants.PASSWORD_REGEX, message =
            "Password doesnt match password policy")
    private String reEnterNewPassword;
}
