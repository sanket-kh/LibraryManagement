package com.librarymanagement.LibraryApplication.models.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FinesDto {
    private String username;
    private String firstName;
    private String lastName;
    private Long isbn;
    private String title;
    private Integer overDue;
    private Integer amount;
    private Boolean isPaid;

}
