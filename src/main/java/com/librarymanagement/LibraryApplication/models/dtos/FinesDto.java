package com.librarymanagement.LibraryApplication.models.dtos;

import lombok.AllArgsConstructor;
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

    public FinesDto(String username, String firstName, String lastName, Long isbn, String title, Integer overDue, Integer amount, Boolean isPaid) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isbn = isbn;
        this.title = title;
        this.overDue = overDue;
        this.amount = amount;
        this.isPaid = isPaid;
    }
}
