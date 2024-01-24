package com.librarymanagement.LibraryApplication.models.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponse  {
    private String role;
    private String accessToken;
}
