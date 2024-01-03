package com.librarymanagement.LibraryApplication.exceptions;

public class NoAuthenticationHeaderException extends RuntimeException {
    public NoAuthenticationHeaderException(String message) {
        super(message);

    }
}
