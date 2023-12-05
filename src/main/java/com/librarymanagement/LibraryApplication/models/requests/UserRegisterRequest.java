package com.librarymanagement.LibraryApplication.models.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterRequest {
    private String firstName;

    private String lastName;

    private String username;

    private String email;

    private Long phone;

    private String address;

    private String password;
}
