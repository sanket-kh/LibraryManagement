package com.librarymanagement.LibraryApplication.models.requests;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookSearchFilterRequest {

    private Long isbn;

    private String title;

    private String author;
}