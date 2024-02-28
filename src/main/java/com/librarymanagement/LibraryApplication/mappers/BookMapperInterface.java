package com.librarymanagement.LibraryApplication.mappers;

import com.librarymanagement.LibraryApplication.entities.Book;
import com.librarymanagement.LibraryApplication.models.dtos.BookDto;
import com.librarymanagement.LibraryApplication.models.dtos.UserBookDto;
import com.librarymanagement.LibraryApplication.models.requests.SaveBookRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapperInterface {
    BookDto mapBookToBookDto(Book book);

    UserBookDto mapBookToUserBookDto(Book book);

    Book mapSaveBookRequestToBook(SaveBookRequest saveBookRequest);

    Book mapUpdateBookRequestToBook(SaveBookRequest saveBookRequest, @MappingTarget Book book);

    List<BookDto> mapBookListToBaseBookDtoList(List<Book> bookList);

    List<UserBookDto> mapBookListToUserBookDtoList(List<Book> bookList);
}
