package com.librarymanagement.LibraryApplication.repositories;

import com.librarymanagement.LibraryApplication.Daos.BookDao;
import com.librarymanagement.LibraryApplication.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepo extends JpaRepository<Book, Long>, BookDao {

    Book getBookByIsbn(Long isbn);

    Book getBookById(Long id);
}
