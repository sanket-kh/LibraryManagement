package com.librarymanagement.LibraryApplication.models.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookTransactionsDto {
    private Long isbn;
    private String title;
    private String author;
    private String username;
    @JsonFormat(pattern ="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime issuedDate;
    @JsonFormat(pattern ="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime returnDate;
}
