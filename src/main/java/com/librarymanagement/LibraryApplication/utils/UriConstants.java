package com.librarymanagement.LibraryApplication.utils;

public class UriConstants {
    public static final String REPORTING_STATISTIC_BASE_URL = "http://localhost:8081/";
    public static final String GET_ALL_BOOKS = "api/v1/report/all-books";
    public static final String GET_ALL_AVAILABLE_BOOKS = "api/v1/report/available-books";
    public static final String GET_ALL_RESERVED_BOOKS = "api/v1/report/reserved-books";
    public static final String GET_COUNT_OF_AVAILABLE_BOOKS = "api/v1/report/book-count/available";
    public static final String GET_COUNT_OF_TOTAL_BOOKS = "api/v1/report/book-count/total";
    public static final String GET_UNIQUE_BOOK_COUNT = "api/v1/report/book/unique/count";
    public static final String GET_COUNT_BORROWED_BOOKS = "api/v1/report/borrowed-books/total/count";
    public static final String GET_COUNT_BORROWED_BOOKS_UNIQUE = "api/v1/report/borrowed-books" +
                                                                 "/unique" +
                                                                 "/count";
    public static final String GET_COUNT_USERS = "api/v1/report/users/total/count";
    public static final String GET_COUNT_ACTIVE_USERS = "api/v1/report/users/active/count";
    public static final String GET_COUNT_LOCKED_USERS = "api/v1/report/users/locked/count";
    public static final String GET_COUNT_DISABLED_USERS = "api/v1/report/users/disabled/count";


}
