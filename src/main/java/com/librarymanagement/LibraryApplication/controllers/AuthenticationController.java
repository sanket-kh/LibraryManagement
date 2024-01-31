package com.librarymanagement.LibraryApplication.controllers;

import com.librarymanagement.LibraryApplication.models.requests.AuthenticationRequest;
import com.librarymanagement.LibraryApplication.models.requests.UserRegisterRequest;
import com.librarymanagement.LibraryApplication.models.responses.DefaultResponse;
import com.librarymanagement.LibraryApplication.services.AuthenticationService;
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
@RestControllerAdvice
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth/user")

public class AuthenticationController {
    private final AuthenticationService authenticationService;


    @PostMapping("/register")
    @Operation(summary = "get a list of associated organization by type",
            responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation",
                            content=@Content(mediaType="application/json",
                                    schema = @Schema(implementation = DefaultResponse.class))),
                    @ApiResponse(responseCode = ResponseConstants.SERVER_ERROR, description = "Internal Server Error",
                            content=@Content(mediaType = "application/json",
                                    schema =@Schema(implementation = DefaultResponse.class))),

            })
    public ResponseEntity<Object> register(@Valid @RequestBody UserRegisterRequest userRegisterRequest) {
        try {
            return authenticationService.registerUser(userRegisterRequest);
        } catch (Exception e) {
            log.error("AuthenticationController :: register",e);
            return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR, "Registration failed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/authentication")
    public ResponseEntity<Object> authenticate(@Valid @RequestBody AuthenticationRequest authenticationRequest) {
        try {
            return authenticationService.authenticate(authenticationRequest);
        } catch (Exception e) {
            log.error("AuthenticationController :: register",e);
            return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR, "Login failed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
