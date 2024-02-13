package com.librarymanagement.LibraryApplication.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserFineDto{
    private String username;
    private String firstName;
    private String lastName;
    private String title;
    private String author;
    private Long isbn;
    private LocalDateTime issueDate;
    private LocalDateTime returnDate;
    private Integer amount;
    private Integer overdue;

}
