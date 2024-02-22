package com.librarymanagement.LibraryApplication.models.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookSearchFilterRequest {

    private Long isbn;

    private String title;

    private String author;
}