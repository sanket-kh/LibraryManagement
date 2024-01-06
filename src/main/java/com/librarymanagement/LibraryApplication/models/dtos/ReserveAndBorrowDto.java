package com.librarymanagement.LibraryApplication.models.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.library.fine.Dtos.FineDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ReserveAndBorrowDto {

    private Boolean reserved;

    private Boolean isIssued;

    private LocalDate issueDate;

    private LocalDate returnDate;

    private UserDto user;

    private BookDto book;

    private FineDto fine;
}
