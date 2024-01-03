package com.librarymanagement.LibraryApplication.models.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExistingBookRequest {
    @Size(min = 3, max= 13)
    private Long isbn;
    @NotNull
    private Integer copies;
}
