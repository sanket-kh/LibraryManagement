package com.librarymanagement.LibraryApplication.repositories;

import com.librarymanagement.LibraryApplication.daos.ReserveAndBorrowDao;
import com.librarymanagement.LibraryApplication.entities.Book;
import com.librarymanagement.LibraryApplication.entities.ReserveAndBorrow;
import com.librarymanagement.LibraryApplication.entities.User;
import com.librarymanagement.LibraryApplication.models.dtos.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReserveAndBurrowRepo extends JpaRepository<ReserveAndBorrow, Long> ,
        PagingAndSortingRepository<ReserveAndBorrow, Long>, ReserveAndBorrowDao {

    List<ReserveAndBorrow> findReserveAndBurrowByBookAndUser(Book book, User user);

    @Query(value = "SELECT R from ReserveAndBorrow R " +
            "WHERE R.book = :book " +
            "AND R.user = :user" +
            " AND R.isIssued = TRUE ")
    ReserveAndBorrow findExistingTransactionByBookAndUser(Book book, User user);

    @Query(value = "SELECT R from ReserveAndBorrow R " +
            "WHERE R.user = :user" +
            " AND R.isIssued = TRUE ")
    List<ReserveAndBorrow> findActiveTransactionsByUser(User user);

    @Query(value = "SELECT R FROM ReserveAndBorrow R " +
            "WHERE R.user = :user " +
            "AND R.book = :book " +
            "AND R.reserved = TRUE ")
    ReserveAndBorrow findReservedTransactionByUserAndBook(User user, Book book);

    @Query(value = "SELECT new com.librarymanagement.LibraryApplication.models.dtos" +
            ".BorrowedBookDto(B.isbn,B.author,B.title, R.issueDate" +
                   ") " +
            "FROM ReserveAndBorrow R " +
            "INNER JOIN Book B ON R.book = B " +
            "INNER  JOIN User U ON R.user = U " +
            "WHERE R.isIssued= TRUE " +
            "AND U.username = :username")
    List<BorrowedBookDto> findBorrowedBooksByUsername(String username);

    @Query(value = "SELECT B.ISBN, B.TITLE, B.AUTHOR " +
            "FROM RESERVE_AND_BORROW R " +
            "INNER JOIN BOOK B ON R.BOOK_ID = B.ID " +
            "INNER  JOIN USER U ON R.USER_ID = U.ID " +
            "WHERE R.IS_ISSUED= FALSE " +
            "AND U.USERNAME = :username " +
            "AND R.IS_RESERVED = TRUE ", nativeQuery = true)
    List<ReservedBookDto> findReservedBooksByUsername(String username);

    @Query(value = """
    SELECT * FROM RESERVE_AND_BORROW R
    WHERE R.RETURN_DATE IS NULL
    AND DATEDIFF(:currentDate, R.ISSUED_DATE)>5
""" , nativeQuery = true)
    List<ReserveAndBorrow> reserveAndBorrowListOfDelayedReturn(LocalDateTime currentDate);

    @Query(value = """
            SELECT new com.librarymanagement.LibraryApplication.models.dtos.UserBookTransaction(B.isbn,B.title,B.author,R.issueDate,R.returnDate)
            FROM ReserveAndBorrow R
            INNER JOIN Book B ON R.book = B
            INNER JOIN User U ON R.user = U
            WHERE U.username = :username
            ORDER BY R.returnDate DESC NULLS FIRST
            """)
    Page<UserBookTransaction> getAllActiveUserTransaction(Pageable pageable,String username);

    @Query(value = """
            SELECT new com.librarymanagement.LibraryApplication.models.dtos.BookTransactionsDto(B.isbn,B.title,B.author,U.username,R.issueDate,R.returnDate)
            FROM ReserveAndBorrow R
            INNER JOIN Book B ON R.book = B
            INNER JOIN User U ON R.user= U
            ORDER BY R.returnDate DESC NULLS FIRST
            """)
    Page<BookTransactionsDto> getAllTransaction(Pageable pageable);

    @Query(value = """
                SELECT new com.librarymanagement.LibraryApplication.models.dtos.FinesDto(U.username, U.firstName, U.lastName, B.isbn , B.title, F.overDue, F.amount,F.isPaid)
            FROM ReserveAndBorrow R
            INNER JOIN Book B ON R.book = B
            INNER JOIN User U ON R.user = U
            INNER JOIN Fine F ON R.fine= F
                """)
    Page<FinesDto> getAllFinesTransaction(Pageable pageable);

    @Query(value = """
SELECT new com.librarymanagement.LibraryApplication.models.dtos.UserBookDto(B.isbn, B.author,B.title)
from ReserveAndBorrow R
inner join Book B on R.book = B
inner join User U on R.user = U
where R.user.username =:username
And R.isIssued = true
""")
    List<UserBookDto> getBooksBorrowedByUser(String username);
}
