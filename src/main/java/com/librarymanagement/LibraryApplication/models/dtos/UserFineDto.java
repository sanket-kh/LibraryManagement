package com.librarymanagement.LibraryApplication.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
