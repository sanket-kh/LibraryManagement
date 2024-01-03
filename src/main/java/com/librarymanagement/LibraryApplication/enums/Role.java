package com.librarymanagement.LibraryApplication.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;

import static com.librarymanagement.LibraryApplication.enums.Permission.*;
@Getter
@RequiredArgsConstructor
public enum Role {
    USER(
           Set.of(USER_READ,USER_WRITE)
    ),
    LIBRARIAN(
            Set.of(LIBRARIAN_READ, LIBRARIAN_WRITE)
    );

    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = new java.util.ArrayList<>(getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }


}
