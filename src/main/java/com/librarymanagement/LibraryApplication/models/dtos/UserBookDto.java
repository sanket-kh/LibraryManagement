package com.librarymanagement.LibraryApplication.models.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserBookDto {
    private Long isbn;
    private String author;
    private String title;
}
