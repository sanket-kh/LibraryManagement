package com.librarymanagement.LibraryApplication.models.dtos;

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
    private Integer overdue;

    public BorrowedBookDto(Long isbn, String author, String title, LocalDate issuedDate) {
        this.isbn = isbn;
        this.author = author;
        this.title = title;
        this.issuedDate = issuedDate;
    }
}

