package com.librarymanagement.LibraryApplication.models.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExistingBookRequest {
    private Long isbn;
    private Integer copies;
}