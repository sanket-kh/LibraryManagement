package com.librarymanagement.LibraryApplication.models.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class TransactionSearchReq {

    private String username;

    @Min(value = 3L , message="ISBN should be min 3 digits")
    @Max(value =13L , message="ISBN should be max 13 digits")
    private Long isbn;

    @JsonFormat(pattern ="yyyy-MM-dd")
    private LocalDate fromDate;

    @JsonFormat(pattern ="yyyy-MM-dd")
    private LocalDate toDate;


}
