package com.librarymanagement.LibraryApplication.services;

import com.librarymanagement.LibraryApplication.models.requests.ChangePasswordRequest;
import com.librarymanagement.LibraryApplication.models.requests.LockUserRequest;
import com.librarymanagement.LibraryApplication.models.requests.UserRegisterRequest;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<Object> addNewUser(UserRegisterRequest registerRequest);

    ResponseEntity<Object> retrieveUser(String username);

    ResponseEntity<Object> failedLoginAttempt(String username);

    ResponseEntity<Object> unlockUserAccount(String username);
    ResponseEntity<Object> lockUserAccount(LockUserRequest lockUserRequest);

    ResponseEntity<Object> changePassword(ChangePasswordRequest username);

}
