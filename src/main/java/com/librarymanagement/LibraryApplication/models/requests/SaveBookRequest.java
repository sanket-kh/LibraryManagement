package com.librarymanagement.LibraryApplication.models.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveBookRequest {

    private Long isbn;
    @NotBlank(message = "title cannot be null")
    private String title;
    @NotBlank(message = "author name cannot be empty")
    private String author;
    private Integer copies;
}
