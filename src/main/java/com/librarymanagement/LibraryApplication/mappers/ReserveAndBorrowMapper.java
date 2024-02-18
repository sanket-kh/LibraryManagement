package com.librarymanagement.LibraryApplication.mappers;


import com.librarymanagement.LibraryApplication.entities.Book;
import com.librarymanagement.LibraryApplication.entities.ReserveAndBorrow;
import com.librarymanagement.LibraryApplication.entities.User;
import com.librarymanagement.LibraryApplication.models.dtos.BookTransactionsDto;
import com.librarymanagement.LibraryApplication.models.dtos.ReserveAndBorrowDto;

import java.util.List;

public class ReserveAndBorrowMapper {
    public static ReserveAndBorrowDto mapToReserveAndBorrowDto(ReserveAndBorrow reserveAndBorrow) {
        ReserveAndBorrowDto reserveAndBorrowDto = new ReserveAndBorrowDto();
        reserveAndBorrowDto.setBook(BookMapper.mapBookToBaseBookDto(reserveAndBorrow.getBook()));
        reserveAndBorrowDto.setUser(UserMapper.mapUserToBaseUserDto(reserveAndBorrow.getUser()));
        reserveAndBorrowDto.setReserved(reserveAndBorrow.getReserved());
        reserveAndBorrowDto.setIsIssued(reserveAndBorrow.getIsIssued());
        reserveAndBorrowDto.setIssueDate(reserveAndBorrow.getIssueDate());
        reserveAndBorrowDto.setReturnDate(reserveAndBorrow.getReturnDate());
        return reserveAndBorrowDto;
    }

    public static BookTransactionsDto mapToBookTransactionDto(ReserveAndBorrow reserveAndBorrow) {
        BookTransactionsDto bookTransactionsDto = new BookTransactionsDto();
        bookTransactionsDto.setIsbn(reserveAndBorrow.getBook().getIsbn());
        bookTransactionsDto.setAuthor(reserveAndBorrow.getBook().getAuthor());
        bookTransactionsDto.setTitle(reserveAndBorrow.getBook().getTitle());
        bookTransactionsDto.setUsername(reserveAndBorrow.getUser().getUsername());
        bookTransactionsDto.setIssuedDate(reserveAndBorrow.getIssueDate());
        if (reserveAndBorrow.getReturnDate() == null) {
            return bookTransactionsDto;
        }
        bookTransactionsDto.setReturnDate(reserveAndBorrow.getReturnDate());
        return bookTransactionsDto;

    }
    public static ReserveAndBorrow mapToReserveAndBorrow(Book book, User user) {
        ReserveAndBorrow reserveAndBorrow = new ReserveAndBorrow();
        reserveAndBorrow.setBook(book);
        reserveAndBorrow.setUser(user);
        reserveAndBorrow.setIsIssued(Boolean.FALSE);
        return reserveAndBorrow;
    }

    public static List<BookTransactionsDto> mapToBookTransactionDto(List<ReserveAndBorrow> reserveAndBorrows) {
        return reserveAndBorrows.stream().map(ReserveAndBorrowMapper::mapToBookTransactionDto).toList();

    }
}