package com.librarymanagement.LibraryApplication.models.dtos.finedto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserFineDto {
    private String username;
    private String firstName;
    private String lastName;
    private String title;
    private String author;
    private Long isbn;
    private LocalDate issueDate;
    private LocalDate returnDate;
    private Integer amount;
    private Integer overdue;

}
