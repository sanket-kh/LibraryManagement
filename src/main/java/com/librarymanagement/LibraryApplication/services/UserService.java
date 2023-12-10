package com.librarymanagement.LibraryApplication.services;

import com.librarymanagement.LibraryApplication.models.requests.UserRegisterRequest;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<Object> registerUser(UserRegisterRequest registerRequest);

    ResponseEntity<Object> retrieveUser(String username);
}
