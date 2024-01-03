package com.librarymanagement.LibraryApplication.models.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class    AuthenticationRequest {
    @NotBlank(message = "username is empty")
    private String username;

    @NotBlank(message = "password is empty")
    private String password;
}
