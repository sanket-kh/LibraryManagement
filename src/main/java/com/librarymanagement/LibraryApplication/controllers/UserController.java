package com.librarymanagement.LibraryApplication.controllers;

import com.librarymanagement.LibraryApplication.models.requests.ChangePasswordRequest;
import com.librarymanagement.LibraryApplication.models.requests.ChangeUserDetailsReq;
import com.librarymanagement.LibraryApplication.models.requests.LockUserRequest;
import com.librarymanagement.LibraryApplication.models.requests.UserRegisterRequest;
import com.librarymanagement.LibraryApplication.models.responses.DefaultResponse;
import com.librarymanagement.LibraryApplication.services.AuthenticationService;
import com.librarymanagement.LibraryApplication.services.UserService;
import com.librarymanagement.LibraryApplication.utils.Constants;
import com.librarymanagement.LibraryApplication.utils.ResponseConstants;
import com.librarymanagement.LibraryApplication.utils.ResponseUtility;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
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
            return  ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR,
                    Constants.EXCEPTION_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/user/update")
    @Operation(summary = "Update user details",
            responses = {
                    @ApiResponse(responseCode = ResponseConstants.OK, description = "successful operation",
                            content=@Content(mediaType = "application/json",
                                    array =@ArraySchema(schema =
                                    @Schema(implementation = DefaultResponse.class)))),
                    @ApiResponse(responseCode = ResponseConstants.SERVER_ERROR, description = "Internal Server Error",
                            content=@Content(mediaType = "application/json",
                                    schema =@Schema(implementation = DefaultResponse.class))),
                    @ApiResponse(responseCode = ResponseConstants.NOT_FOUND, description = "User not found",
                            content=@Content(mediaType = "application/json",
                                    schema =@Schema(implementation = DefaultResponse.class))),

            })
    public ResponseEntity<Object> updateUserDetails(@RequestBody ChangeUserDetailsReq changeUserDetailsReq) {
        try {
            return userService.updateUserDetails(changeUserDetailsReq);
        } catch (Exception e) {
            log.error("UserController :: updateUserDetails", e);
            return  ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR,
                    Constants.EXCEPTION_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/exists")
    public ResponseEntity<Object> usernameExists(@RequestParam String username) {
        try {
            return userService.userNameExists(username);
        } catch (Exception e) {
            log.error("UserController :: usernameExists", e);
            return  ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR,
                    Constants.EXCEPTION_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user/details")
    public ResponseEntity<Object> retrieveUser(@RequestParam String username) {
        try {
            return userService.retrieveUser(username);
        } catch (Exception e) {
            log.error("UserController :: retrieveUser", e);
            return  ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR,
                    Constants.EXCEPTION_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<Object> searchUserByUsername(@RequestParam String username) {
        try {
            return userService.searchUserByUsername(username);
        } catch (Exception e) {
            log.error("UserController :: searchUserByUsername", e);
            return  ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR,
                    Constants.EXCEPTION_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-all")
    public ResponseEntity<Object> getAllUsers() {
        try {
            return userService.getAll();
        } catch (Exception e) {
            log.error("UserController :: getAllUsers", e);
            return  ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR,
                    Constants.EXCEPTION_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/unlock")
    public ResponseEntity<Object> unlockUser(@RequestParam String username) {
        try {
            return userService.unlockUserAccount(username);
        } catch (Exception e) {
            log.error("UserController :: unlockUser", e);
            return  ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR,
                    Constants.EXCEPTION_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/lock")
    public ResponseEntity<Object> lockUser(@RequestBody LockUserRequest lockUserRequest) {
        try {
            return userService.lockUserAccount(lockUserRequest);
        } catch (Exception e) {
            log.error("UserController :: lockUser", e);
            return ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR,
                    Constants.EXCEPTION_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/user/change-password")
    public ResponseEntity<Object> changePassword(@RequestBody @Valid ChangePasswordRequest changePasswordRequest) {
        try {
            return userService.changePassword(changePasswordRequest);
        } catch (Exception e) {
            log.error("UserController :: changePassword", e);
            return  ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR,
                    Constants.EXCEPTION_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/locked")
    public ResponseEntity<Object> getAllLockedUsers() {
        try {
            return userService.getLockedUsers();
        } catch (Exception e) {
            log.error("UserController :: getAllLockedUsers", e);
            return  ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR,
                    Constants.EXCEPTION_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/details")
    public ResponseEntity<Object> getUserDetailsByUsername(@RequestParam String username) {
        try {
            return userService.getUserDetailByUsername(username);
        } catch (Exception e) {
            log.error("UserController :: getUserDetailsByUsername", e);
            return  ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR,
                    Constants.EXCEPTION_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/setup-admin")
    public ResponseEntity<Object> setUpAdmin(@RequestBody UserRegisterRequest userRegisterRequest){
        try {
            return authenticationService.registerLibrarian(userRegisterRequest);
        } catch (Exception e) {
            log.error("UserController :: setUpAdmin", e);
            return  ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR,
                    Constants.EXCEPTION_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}


