package com.librarymanagement.LibraryApplication.repositories;

import com.librarymanagement.LibraryApplication.entities.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepo extends JpaRepository<Book, Long> {

    Book getBookByIsbn(Long isbn);

    @Query(value = """
            select B from Book B
            where B.isAvailable = true
            """)
    Page<Book> getAvailableBooks(Pageable pageable);

    Page<Book> findAll(Specification<Book> specification, Pageable pageable);

}
