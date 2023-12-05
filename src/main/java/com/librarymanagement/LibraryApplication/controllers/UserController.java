package com.librarymanagement.LibraryApplication.controllers;

import com.librarymanagement.LibraryApplication.models.requests.UserRegisterRequest;
import com.librarymanagement.LibraryApplication.services.UserService;
import com.librarymanagement.LibraryApplication.utils.ResponseConstants;
import com.librarymanagement.LibraryApplication.utils.ResponseUtility;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@AllArgsConstructor
@RequestMapping(value = "api/v1/users")
public class UserController {
    private UserService userService;
    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@RequestBody UserRegisterRequest userRegisterRequest){
        try {
            return userService.registerUser(userRegisterRequest);
        } catch (Exception e) {
            log.error("UserController :: registerUser", e);
            return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR,
                    "Server Error"), HttpStatus.OK);
        }
    }

    @GetMapping("/")
    public ResponseEntity<Object> retrieveUser(@RequestParam String username){
        try {
            return userService.retrieveUser(username);
        } catch (Exception e) {
            log.error("UserController :: retrieveUser", e);
            return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR,
                    "Server Error"), HttpStatus.OK);
        }
    }
}
