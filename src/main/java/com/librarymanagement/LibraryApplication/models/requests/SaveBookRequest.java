package com.librarymanagement.LibraryApplication.models.requests;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveBookRequest {
    @Min(value = 3L , message="ISBN should be min 3 digits")
    @Max(value =13L , message="ISBN should be max 13 digits")
    private Long isbn;
    @NotBlank(message = "title cannot be null")
    private String title;
    @NotBlank(message = "author name cannot be empty")
    private String author;
    private Integer copies;
}
