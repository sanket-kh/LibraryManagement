package com.librarymanagement.LibraryApplication.models.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class TransactionSearchReq {
    private String username;
    private Long isbn;
    @JsonFormat(pattern ="yyyy-M-d")
    private LocalDate date;
}
