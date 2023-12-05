package com.librarymanagement.LibraryApplication.models.dtos.bookdtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookDto {
    private Long isbn;
    private String title;
    private String author;
    private Integer copies;
}
