package com.librarymanagement.LibraryApplication.daos;


import com.librarymanagement.LibraryApplication.entities.Book;
import com.librarymanagement.LibraryApplication.models.requests.BookSearchFilterRequest;

import java.util.List;

public interface BookDao {
    List<Book> bookSearchFilter(BookSearchFilterRequest bookSearchFilterRequest);
}
