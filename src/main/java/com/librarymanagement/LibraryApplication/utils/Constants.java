package com.librarymanagement.LibraryApplication.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {
    public static final Integer FINE_PER_DAY = 1;
    public static final Integer PAGE_SIZE=10;
    public static final String DEFAULT_PAGE_SIZE="10";
    public static final Integer MAX_BORROW_DURATION_PER_BOOK = 90;
    public static final Integer BORROW_LIMIT_PER_USER = 2;
    public static final Integer MAX_FAILED_LOGIN_ATTEMPT = 4;
    public static final Integer TIMEOUT_DURATION =5000;

    public static final String EXCEPTION_MESSAGE = "Server Error";
    public static final String[] PUBLIC_ACCESS_URI = new String[] {
            "/api/v1/auth/user/authentication",
            "/api/v1/auth/user/register",
            "/v3/api-docs",
            "/swagger-ui/**",
            "/swagger-ui/index.html",
            "/swagger-ui/swagger-ui.css",
            "/swagger-ui/favicon-32x32.png",
            "/v3/api-docs/swagger-config",
            "/v3/swagger-resources",
            "/swagger-ui/swagger-initializer.js",
            "/webjars/**"};
    public static final String[] USER_ACCESS_URI = new String[] {
            "/api/v1/books/user/**",
            "/api/v1/reserve",
            "/api/v1/users/user/**",
            "/api/v1/borrow",
            "/api/v1/return",
            "/api/v1/user/borrowed-books",
            "/api/v1/user/cancel-reserve",
            "/api/v1/user/transactions",
            "/api/v1/fine/user/**",
    "/api/v1/user/change-password"};

    public static final int PAGE_NUMBER = 0;
}
