package com.librarymanagement.LibraryApplication.models.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordRequest {
private String username;
private String currentPassword;
private String newPassword;
private String reEnterNewPassword;
}
