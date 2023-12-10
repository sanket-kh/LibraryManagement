package com.librarymanagement.LibraryApplication.utils;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class Constants {
    public static final Integer FINE_PER_DAY = 1;
    public static final Integer MAX_BORROW_DURATION_PER_BOOK = 90;
    public static final Integer BORROW_LIMIT_PER_USER = 2;
    public static final Integer MAX_RESERVE_LIMIT_PER_USER = 2;
    public static final String[] PUBLIC_ACCESS_URI = new String[] {"/api/v1/auth/user/**",
            "/v3/api-docs",
            "/v3/swagger-resources",
            "/api/v1/auth/user/**",
            "/swagger-ui/**",
            "/webjars/**"};
    public static final String[] USER_ACCESS_URI = new String[] {"/api/v1/books/search",
            "/api/v1/books/user/**",
            "/api/v1/reserve",
            "/api/v1/borrow",
            "/api/v1/return",
            "/api/v1/user/borrowed-books",
            "/api/v1/user/cancel-reserve"};
    
}
