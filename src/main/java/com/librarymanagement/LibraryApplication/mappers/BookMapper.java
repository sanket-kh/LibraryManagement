package com.librarymanagement.LibraryApplication.mappers;


import com.librarymanagement.LibraryApplication.entities.Book;
import com.librarymanagement.LibraryApplication.models.dtos.BookDto;
import com.librarymanagement.LibraryApplication.models.dtos.UserBookDto;
import com.librarymanagement.LibraryApplication.models.requests.SaveBookRequest;
import com.librarymanagement.LibraryApplication.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class BookMapper {

    public static BookDto mapBookToBaseBookDto(Book book) {
        BookDto bookDto = new BookDto();
        bookDto.setCopies(book.getCopies());
        bookDto.setIsbn(book.getIsbn());
        bookDto.setTitle(book.getTitle());
        bookDto.setAuthor(book.getAuthor());
        bookDto.setIsAvailable(book.getIsAvailable());
        return bookDto;
    }


    public static UserBookDto mapBookToUserBookDto(Book book) {
        UserBookDto userBookDto = new UserBookDto();
        userBookDto.setIsbn(book.getIsbn());
        userBookDto.setTitle(book.getTitle());
        userBookDto.setAuthor(book.getAuthor());
        return userBookDto;
    }

    public static Book mapSaveBookRequestToBook(SaveBookRequest saveBookRequest) {
        Book book = new Book();
        book.setIsbn(saveBookRequest.getIsbn());
        book.setTitle(Utils.convertToTitleCaseIteratingChars(saveBookRequest.getTitle()));
        book.setCopies(saveBookRequest.getCopies());
        book.setAuthor(Utils.convertToTitleCaseIteratingChars(saveBookRequest.getAuthor()));
        book.setIsAvailable(Boolean.TRUE);
        return book;
    }
    public static Book mapUpdateBookRequestToBook(SaveBookRequest saveBookRequest, Book book) {
        book.setIsbn(saveBookRequest.getIsbn());
        book.setTitle(Utils.convertToTitleCaseIteratingChars(saveBookRequest.getTitle()));
        book.setCopies(saveBookRequest.getCopies());
        book.setAuthor(Utils.convertToTitleCaseIteratingChars(saveBookRequest.getAuthor()));
        book.setIsAvailable(Boolean.TRUE);
        return book;
    }


    public static List<BookDto> mapBookListToBaseBookDtoList(List<Book> bookList) {
        List<BookDto> bookDtoList = new ArrayList<>();
        for (Book book : bookList) {
            BookDto bookDto = BookMapper.mapBookToBaseBookDto(book);
            bookDtoList.add(bookDto);
        }
        return bookDtoList;
    }
    public static List<UserBookDto> mapBookListToUserBookDtoList(List<Book> bookList) {
        List<UserBookDto> bookDtoList = new ArrayList<>();
        for (Book book : bookList) {
            UserBookDto userBookDto = BookMapper.mapBookToUserBookDto(book);
            bookDtoList.add(userBookDto);
        }
        return bookDtoList;
    }


}
