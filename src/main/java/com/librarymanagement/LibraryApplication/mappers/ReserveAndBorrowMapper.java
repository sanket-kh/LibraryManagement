package com.librarymanagement.LibraryApplication.mappers;


import com.librarymanagement.LibraryApplication.entities.ReserveAndBorrow;
import com.librarymanagement.LibraryApplication.models.dtos.ReserveAndBorrowDto;

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
}
