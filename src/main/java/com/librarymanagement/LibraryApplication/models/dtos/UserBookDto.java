package com.librarymanagement.LibraryApplication.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserBookDto {
    private Long isbn;
    private String author;
    private String title;
}
