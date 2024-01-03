package com.librarymanagement.LibraryApplication.repositories;

import com.librarymanagement.LibraryApplication.entities.LibrarianAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibrarianAccountRepo extends JpaRepository<LibrarianAccount, Long> {
}
