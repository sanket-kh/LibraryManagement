package com.librarymanagement.LibraryApplication.models.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class UserBookTransaction {
    private Long isbn;
    private String title;
    private String author;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate issuedDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate returnDate;
}
