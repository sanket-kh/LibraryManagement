package com.librarymanagement.LibraryApplication.services;

import com.librarymanagement.LibraryApplication.models.requests.ChangePasswordRequest;
import com.librarymanagement.LibraryApplication.models.requests.ChangeUserDetailsReq;
import com.librarymanagement.LibraryApplication.models.requests.LockUserRequest;
import com.librarymanagement.LibraryApplication.models.requests.UserRegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService extends UserDetailsService {
    ResponseEntity<Object> addNewUser(UserRegisterRequest registerRequest);

    ResponseEntity<Object> setUpAdmin(UserRegisterRequest userRegisterRequest);


    ResponseEntity<Object> retrieveUser(String username);

    ResponseEntity<Object> failedLoginAttempt(String username);

    ResponseEntity<Object> unlockUserAccount(String username);

    ResponseEntity<Object> lockUserAccount(LockUserRequest lockUserRequest);

    ResponseEntity<Object> changePassword(ChangePasswordRequest username);

    ResponseEntity<Object> userNameExists(String username);

    ResponseEntity<Object> searchUserByUsername(String username);

    ResponseEntity<Object> getAll();

    ResponseEntity<Object> getLockedUsers();

    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    ResponseEntity<Object> getUserDetailByUsername(String username);

    ResponseEntity<Object> updateUserDetails(ChangeUserDetailsReq changeUserDetailsReq);

}
