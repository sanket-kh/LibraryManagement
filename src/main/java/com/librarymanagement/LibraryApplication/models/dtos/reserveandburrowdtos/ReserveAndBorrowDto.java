package com.librarymanagement.LibraryApplication.models.dtos.reserveandburrowdtos;

import com.library.fine.Dtos.FineDto;
import com.librarymanagement.LibraryApplication.models.dtos.bookdtos.BookDto;
import com.librarymanagement.LibraryApplication.models.dtos.userdtos.UserDto;
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
