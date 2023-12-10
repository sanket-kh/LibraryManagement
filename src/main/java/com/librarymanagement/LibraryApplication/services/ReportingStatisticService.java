package com.librarymanagement.LibraryApplication.services;

import org.springframework.http.ResponseEntity;

public interface ReportingStatisticService {
    ResponseEntity<Object> getListOfAllBooks();

    ResponseEntity<Object> getListOfAllAvailableBooks();

    ResponseEntity<Object> getListOfAllReservedBooks();

    ResponseEntity<Object> getCountOfTotalBooks();

    ResponseEntity<Object> getCountOfAvailableBooks();

}
