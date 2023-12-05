package com.librarymanagement.LibraryApplication.Daos;

import com.librarymanagement.LibraryApplication.entities.Book;
import com.librarymanagement.LibraryApplication.models.requests.BookSearchFilterRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.util.List;

public class BookDaoImpl implements BookDao {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Book> bookSearchFilter(BookSearchFilterRequest bookSearchFilterRequest) {


        String BOOK_SEARCH_FILTER_QUERY = "SELECT B " +
                                          "FROM Book B " +
                                          "WHERE 1=1 ";
        if (bookSearchFilterRequest.getIsbn() != null && bookSearchFilterRequest.getIsbn() != 0) {
            BOOK_SEARCH_FILTER_QUERY = BOOK_SEARCH_FILTER_QUERY +
                                       "AND B.isbn = :isbn ";
        }
        if (bookSearchFilterRequest.getTitle() != null && !bookSearchFilterRequest.getTitle().isEmpty()) {
            BOOK_SEARCH_FILTER_QUERY = BOOK_SEARCH_FILTER_QUERY +
                                       " AND B.title like :title ";
        }
        if (bookSearchFilterRequest.getAuthor() != null && !bookSearchFilterRequest.getAuthor().isEmpty()) {
            BOOK_SEARCH_FILTER_QUERY = BOOK_SEARCH_FILTER_QUERY +
                                       " AND B.author like :author ";
        }


        System.out.println(" Query : " + BOOK_SEARCH_FILTER_QUERY);
        Query searchQuery = this.entityManager.createQuery(BOOK_SEARCH_FILTER_QUERY);

        if (bookSearchFilterRequest.getIsbn() != null && bookSearchFilterRequest.getIsbn() != 0) {
            searchQuery.setParameter("isbn", bookSearchFilterRequest.getIsbn());
        }
        if (bookSearchFilterRequest.getTitle() != null && !bookSearchFilterRequest.getTitle().isEmpty()) {
            searchQuery.setParameter("title", "%" + bookSearchFilterRequest.getTitle().trim() + "%");
        }
        if (bookSearchFilterRequest.getAuthor() != null && !bookSearchFilterRequest.getAuthor().isEmpty()) {
            searchQuery.setParameter("author", "%" + bookSearchFilterRequest.getAuthor().trim() + "%");
        }

        return (List<Book>) searchQuery.getResultList();

    }
}
