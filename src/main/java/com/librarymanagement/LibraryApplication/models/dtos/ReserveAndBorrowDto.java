package com.librarymanagement.LibraryApplication.models.dtos;

import com.library.fine.Dtos.FineDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReserveAndBorrowDto {

    private Boolean reserved;

    private Boolean isIssued;

    private LocalDateTime issueDate;

    private LocalDateTime returnDate;

    private UserDto user;

    private BookDto book;

    private FineDto fine;
}
