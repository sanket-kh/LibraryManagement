package com.librarymanagement.LibraryApplication.repositories;


import com.librarymanagement.LibraryApplication.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
    User findUserByUsername(String username);
}
