package com.librarymanagement.LibraryApplication.controllers;

import com.librarymanagement.LibraryApplication.models.requests.ChangePasswordRequest;
import com.librarymanagement.LibraryApplication.models.requests.ChangeUserDetailsReq;
import com.librarymanagement.LibraryApplication.models.requests.LockUserRequest;
import com.librarymanagement.LibraryApplication.models.requests.UserRegisterRequest;
import com.librarymanagement.LibraryApplication.services.AuthenticationService;
import com.librarymanagement.LibraryApplication.services.UserService;
import com.librarymanagement.LibraryApplication.utils.ResponseConstants;
import com.librarymanagement.LibraryApplication.utils.ResponseUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequiredArgsConstructor
@RestControllerAdvice
@RequestMapping(value = "api/v1/users")
public class UserController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@RequestBody UserRegisterRequest userRegisterRequest) {
        try {
            return userService.addNewUser(userRegisterRequest);
        } catch (Exception e) {
            log.error("UserController :: registerUser", e);
            return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR,
                    "Server Error"), HttpStatus.OK);
        }
    }

    @PostMapping("/user/update")
    public ResponseEntity<Object> updateUserDetails(@RequestBody ChangeUserDetailsReq changeUserDetailsReq) {
        try {
            return userService.updateUserDetails(changeUserDetailsReq);
        } catch (Exception e) {
            log.error("UserController :: updateUserDetails", e);
            return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR,
                    "Server Error"), HttpStatus.OK);
        }
    }


    @GetMapping("/exists")
    public ResponseEntity<Object> usernameExists(@RequestParam String username) {
        return userService.userNameExists(username);
    }

    @GetMapping("/user/details")
    public ResponseEntity<Object> retrieveUser(@RequestParam String username) {
        try {
            return userService.retrieveUser(username);
        } catch (Exception e) {
            log.error("UserController :: retrieveUser", e);
            return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR,
                    "Server Error"), HttpStatus.OK);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<Object> searchUserByUsername(@RequestParam String username) {
        return userService.searchUserByUsername(username);
    }

    @GetMapping("/get-all")
    public ResponseEntity<Object> getAllUsers() {
        return userService.getAll();
    }

    @PostMapping("/unlock")
    public ResponseEntity<Object> unlockUser(@RequestParam String username) {
        return userService.unlockUserAccount(username);
    }

    @PostMapping("/lock")
    public ResponseEntity<Object> lockUser(@RequestBody LockUserRequest lockUserRequest) {
        return userService.lockUserAccount(lockUserRequest);
    }

    @PostMapping("/user/change-password")
    public ResponseEntity<Object> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        return userService.changePassword(changePasswordRequest);
    }

    @GetMapping("/locked")
    public ResponseEntity<Object> getAllLockedUsers() {
        return userService.getLockedUsers();
    }

    @GetMapping("/details")
    public ResponseEntity<Object> getUserDetailsByUsername(@RequestParam String username) {
        return userService.getUserDetailByUsername(username);
    }

    @PostMapping("/setup-admin")
    public ResponseEntity<Object> setUpAdmin(@RequestBody UserRegisterRequest userRegisterRequest){
        return authenticationService.registerLibrarian(userRegisterRequest);
    }

}


