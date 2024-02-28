package com.librarymanagement.LibraryApplication.mappers;

import com.librarymanagement.LibraryApplication.entities.ReserveAndBorrow;
import com.librarymanagement.LibraryApplication.models.dtos.BookTransactionsDto;
import com.librarymanagement.LibraryApplication.models.dtos.ReserveAndBorrowDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReserveAndBorrowMapperInterface {

    ReserveAndBorrowDto mapToReserveAndBorrowDto(ReserveAndBorrow reserveAndBorrow);
    @Mapping(target = "author" , source = "reserveAndBorrow.book.author")
    @Mapping(target = "isbn" , source = "reserveAndBorrow.book.isbn")
    @Mapping(target = "title" , source = "reserveAndBorrow.book.title")
    @Mapping(target = "issuedDate" , source = "reserveAndBorrow.issueDate")
    @Mapping(target = "username" , source = "reserveAndBorrow.user.username")
    BookTransactionsDto mapToBookTransactionDto(ReserveAndBorrow reserveAndBorrow);
    List<BookTransactionsDto> mapToBookTransactionDto(List<ReserveAndBorrow> reserveAndBorrows);
}
