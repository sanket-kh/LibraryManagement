package com.librarymanagement.LibraryApplication.models.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReserveRequest {
    @NotBlank(message = "username is empty")
    private String username;
    private Long isbn;
}
