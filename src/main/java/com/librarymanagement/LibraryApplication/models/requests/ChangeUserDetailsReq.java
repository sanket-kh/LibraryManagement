package com.librarymanagement.LibraryApplication.models.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeUserDetailsReq {
    private String firstName;
    private String lastName;
    private String email;
    private Long phone;
    private String address;
}
