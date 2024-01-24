package com.librarymanagement.LibraryApplication.models.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ManageUserDto {
    private String firstName;

    private String lastName;

    private String username;

    private String email;

    private Long phone;

    private String address;

    private Boolean status;
    private String role;

    private Boolean isNotLocked;
    private String remark;

}
