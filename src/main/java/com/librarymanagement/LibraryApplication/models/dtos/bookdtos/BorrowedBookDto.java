package com.librarymanagement.LibraryApplication.models.dtos.bookdtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BorrowedBookDto {
    private Long isbn;
    private String author;
    private String title;
    private LocalDate issuedDate;

}
