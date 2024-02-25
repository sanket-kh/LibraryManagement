package com.librarymanagement.LibraryApplication.daos;

import com.librarymanagement.LibraryApplication.entities.Book;
import com.librarymanagement.LibraryApplication.models.requests.BookSearchFilterRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.extern.log4j.Log4j2;

import java.util.List;

/**
 * @deprecated As of 2024/02/22, replaced by jpa specifications
 **/

@Log4j2
@Deprecated(since = "2024/02/22")
public class BookDaoImpl implements BookDao {
    @PersistenceContext
    EntityManager entityManager;

    private static String getQueryString(BookSearchFilterRequest bookSearchFilterRequest) {

        String BOOK_SEARCH_FILTER_QUERY = "SELECT B " + "FROM Book B " + "WHERE 1=1 ";
        if (bookSearchFilterRequest.getIsbn() != null && bookSearchFilterRequest.getIsbn() != 0) {
            BOOK_SEARCH_FILTER_QUERY = BOOK_SEARCH_FILTER_QUERY + "AND B.isbn = :isbn ";
        }
        if (bookSearchFilterRequest.getTitle() != null && !bookSearchFilterRequest.getTitle()
                .isEmpty()) {
            BOOK_SEARCH_FILTER_QUERY = BOOK_SEARCH_FILTER_QUERY + " AND B.title like :title ";
        }
        if (bookSearchFilterRequest.getAuthor() != null && !bookSearchFilterRequest.getAuthor()
                .isEmpty()) {
            BOOK_SEARCH_FILTER_QUERY = BOOK_SEARCH_FILTER_QUERY + " AND B.author like :author ";
        }
        return BOOK_SEARCH_FILTER_QUERY;
    }

    @Override
    public List<Book> bookSearchFilter(BookSearchFilterRequest bookSearchFilterRequest) {

        String BOOK_SEARCH_FILTER_QUERY = getQueryString(bookSearchFilterRequest);
        Query searchQuery = this.entityManager.createQuery(BOOK_SEARCH_FILTER_QUERY);

        if (bookSearchFilterRequest.getIsbn() != null && bookSearchFilterRequest.getIsbn() != 0) {
            searchQuery.setParameter("isbn", bookSearchFilterRequest.getIsbn());
        }
        if (bookSearchFilterRequest.getTitle() != null && !bookSearchFilterRequest.getTitle()
                .isEmpty()) {
            searchQuery.setParameter("title",
                    "%" + bookSearchFilterRequest.getTitle().trim() + "%");
        }
        if (bookSearchFilterRequest.getAuthor() != null && !bookSearchFilterRequest.getAuthor()
                .isEmpty()) {
            searchQuery.setParameter("author",
                    "%" + bookSearchFilterRequest.getAuthor().trim() + "%");
        }

        return (List<Book>) searchQuery.getResultList();


    }
}
