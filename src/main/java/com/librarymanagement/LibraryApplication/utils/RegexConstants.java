package com.librarymanagement.LibraryApplication.utils;

public class RegexConstants {
    public static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])" +
                                                "[A-Za-z\\d@$!%*#?&]{8,20}$";
    public static final String TITLE_CASE_REGEX = "\\b(?:[A-Z][a-z]*\\b\\s*)+";
    public static final String ADDRESS_REGEX = "^[a-zA-Z0-9, -]*[a-zA-Z][a-zA-Z0-9, -]*$";

    public static final String PHONE_REGEX = "^[9][0-9]{9}$";

    public static final String ACCOUNT_NUMBER_REGEX = "^[0-9]{9,20}$";

    public static final String USERNAME_REGEX = "^[A-Za-z][A-Za-z0-9_]{3,20}$";

    public static final String ISBN_REGEX = "^[0-9]";
}