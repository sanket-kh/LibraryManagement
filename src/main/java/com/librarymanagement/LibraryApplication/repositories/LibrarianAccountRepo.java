package com.librarymanagement.LibraryApplication.repositories;

import com.librarymanagement.LibraryApplication.entities.AccountType;
import com.librarymanagement.LibraryApplication.entities.LibrarianAccount;
import com.librarymanagement.LibraryApplication.entities.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LibrarianAccountRepo extends JpaRepository<LibrarianAccount, Long> {
    @Query(value = """
            SELECT L FROM LibrarianAccount L
            WHERE L.librarian.username = :username
            AND L.active='Y'
            """)
    List<LibrarianAccount> getAdminAccountDetailsByUsername(String username);

    @Query(value = """
            SELECT L FROM LibrarianAccount L
            WHERE L.librarian = :user
            AND L.accountType=:accountType
            AND L.active='Y'
            """)
    LibrarianAccount getLibrarianAccountByAccountTypeAndLibrarian(AccountType accountType, User user);

    @Modifying
    @Transactional
    @Query(value = """
            update LibrarianAccount L
            set L.active='N'
            where L.librarian=:user
            and L.active='Y'
            """)
    Integer makeAccountDetailInactive(User user);

    @Modifying
    @Transactional
    @Query(value = """
            update LibrarianAccount L
            set L.active='N'
            where L.librarian=:user
            and L.accountType.accountTypeName=:accType
            """)
    void makeAccountTypeInactiveByUser(String accType, User user);

    @Query(value = """
            SELECT L FROM LibrarianAccount L
            INNER JOIN AccountType A ON A = L.accountType
            WHERE L.active='L' AND A.accountTypeName= :accountTypeName
            """)
    LibrarianAccount getClearFineAccountByAccountTypeAndUsername(String accountTypeName);
}
