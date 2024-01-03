package com.librarymanagement.LibraryApplication.services;

import com.librarymanagement.LibraryApplication.models.requests.AuthenticationRequest;
import com.librarymanagement.LibraryApplication.models.requests.UserRegisterRequest;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {
    ResponseEntity<Object> register(UserRegisterRequest userRegisterRequest);
    ResponseEntity<Object> authenticate(AuthenticationRequest authenticationRequest);

}
