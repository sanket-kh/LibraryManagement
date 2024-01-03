package com.librarymanagement.LibraryApplication.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Permission {
    USER_READ("user:read"),
    USER_WRITE("user:write"),
    LIBRARIAN_READ("librarian:read"),
    LIBRARIAN_WRITE("librarian:write");

    private final String permission;
}
