package com.librarymanagement.LibraryApplication.repositories;

import com.librarymanagement.LibraryApplication.entities.Fine;
import com.librarymanagement.LibraryApplication.models.dtos.UserFineDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FineRepo extends JpaRepository<Fine, Long> {
    @Query(value = "SELECT SUM(F.AMOUNT)" +
            "FROM RESERVE_AND_BORROW R " +
            "INNER JOIN BOOK B ON R.BOOK_ID = B.ID " +
            "INNER  JOIN USER U ON R.USER_ID = U.ID " +
            "INNER JOIN FINE F ON R.ID = F.RESERVE_AND_BORROW_ID " +
            "WHERE F.IS_PAID = FALSE " +
            "AND U.USERNAME = :username", nativeQuery = true)
    Integer getTotalFineOwedByUser(String username);

    @Query(value = "UPDATE FINE F " +
            "INNER JOIN RESERVE_AND_BORROW R ON F.RESERVE_AND_BORROW_ID = R.ID " +
            "INNER JOIN BOOK B ON R.BOOK_ID = B.ID " +
            "INNER  JOIN USER U ON R.USER_ID = U.ID " +
            "SET F.IS_PAID = TRUE " +
            "WHERE U.USERNAME = :username", nativeQuery = true)
    void clearFinesForUsername(String username);

    @Query(value = "SELECT new " +
            "com.librarymanagement.LibraryApplication.models.dtos.UserFineDto " +
            "(U.username, U.firstName, U.lastName, B.title, B.author, B.isbn, R.issueDate, R.returnDate, F.amount , F.overDue) " +
            "FROM ReserveAndBorrow R " +
            "INNER JOIN Book B ON R.book = B " +
            "INNER JOIN User U ON R.user = U " +
            "INNER JOIN Fine F ON R.fine= F " +
            "WHERE U.username =:username")
    List<UserFineDto> getFinesByUsername(String username);
}
