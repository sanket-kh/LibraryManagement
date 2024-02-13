package com.librarymanagement.LibraryApplication.models.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class LibrarianAccountDto {
    private String accountTypeName;
    private String accountAssociatedOrganizationName;
    private String accountName;
    private Long accountNumber;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime recordedDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime modifiedDate;
}
