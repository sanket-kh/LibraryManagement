package com.librarymanagement.LibraryApplication.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {
    USER_READ("user:read"),
    USER_WRITE("user:write"),
    LIBRARIAN_READ("librarian:read"),
    LIBRARIAN_WRITE("librarian:write");

    @Getter
    private final String permission;
}
