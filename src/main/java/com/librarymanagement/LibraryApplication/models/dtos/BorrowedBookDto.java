package com.librarymanagement.LibraryApplication.models.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BorrowedBookDto {
    private Long isbn;
    private String author;
    private String title;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime issuedDate;
    private Integer overdue;

    public BorrowedBookDto(Long isbn, String author, String title, LocalDateTime issuedDate) {
        this.isbn = isbn;
        this.author = author;
        this.title = title;
        this.issuedDate = issuedDate;
    }
}

