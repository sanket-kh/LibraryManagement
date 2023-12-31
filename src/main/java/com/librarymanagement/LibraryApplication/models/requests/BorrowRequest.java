package com.librarymanagement.LibraryApplication.models.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BorrowRequest {
    @NotBlank(message = "username is blank")
    private String username;
    @NotNull(message = "isbn is null")
    private Long isbn;

}
