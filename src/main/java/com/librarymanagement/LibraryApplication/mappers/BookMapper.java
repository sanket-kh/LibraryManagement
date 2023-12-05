package com.librarymanagement.LibraryApplication.mappers;


import com.librarymanagement.LibraryApplication.entities.Book;
import com.librarymanagement.LibraryApplication.models.dtos.bookdtos.BookDto;
import com.librarymanagement.LibraryApplication.models.dtos.bookdtos.UserBookDto;
import com.librarymanagement.LibraryApplication.models.requests.SaveBookRequest;

import java.util.ArrayList;
import java.util.List;

public class BookMapper {

    public static BookDto mapBookToBaseBookDto(Book book) {
        BookDto bookDto = new BookDto();
        bookDto.setCopies(book.getCopies());
        bookDto.setIsbn(book.getIsbn());
        bookDto.setTitle(book.getTitle());
        bookDto.setAuthor(book.getAuthor());
        return bookDto;
    }

    public static Book mapBaseBookDtoToBook(BookDto bookDto) {
        Book book = new Book();
        book.setCopies(bookDto.getCopies());
        book.setIsbn(bookDto.getIsbn());
        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        return book;
    }

    public static UserBookDto mapBookToUserBookDto(Book book) {
        UserBookDto userBookDto = new UserBookDto();
        userBookDto.setIsbn(book.getIsbn());
        userBookDto.setTitle(book.getTitle());
        userBookDto.setAuthor(book.getAuthor());
        return userBookDto;
    }

    public static Book mapUserBookDtoToBook(UserBookDto userBookDto) {
        Book book = new Book();
        book.setIsbn(userBookDto.getIsbn());
        book.setTitle(userBookDto.getTitle());
        book.setAuthor(userBookDto.getAuthor());
        return book;
    }

    public static Book mapSaveBookRequestToBook(SaveBookRequest saveBookRequest) {
        Book book = new Book();
        book.setIsbn(saveBookRequest.getIsbn());
        book.setTitle(saveBookRequest.getTitle());
        book.setCopies(saveBookRequest.getCopies());
        book.setAuthor(saveBookRequest.getAuthor());
        book.setIsAvailable(Boolean.TRUE);
        return book;
    }

    public static SaveBookRequest mapBookToSaveBookRequest(Book book) {
        SaveBookRequest saveBookRequest = new SaveBookRequest();
        saveBookRequest.setTitle(book.getTitle());
        saveBookRequest.setIsbn(book.getIsbn());
        saveBookRequest.setCopies(book.getCopies());
        saveBookRequest.setAuthor(book.getAuthor());
        return saveBookRequest;
    }

    public static List<BookDto> mapBookListToBaseBookDtoList(List<Book> bookList) {
        List<BookDto> bookDtoList = new ArrayList<>();
        for (Book book : bookList) {
            BookDto bookDto = BookMapper.mapBookToBaseBookDto(book);
            bookDtoList.add(bookDto);
        }
        return bookDtoList;
    }

}
