package com.librarymanagement.LibraryApplication.models.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class LibrarianAccountDto {
    private String accountTypeName;
    private String accountAssociatedOrganizationName;
    private String accountName;
    private Long accountNumber;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate recordedDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate modifiedDate;
}
