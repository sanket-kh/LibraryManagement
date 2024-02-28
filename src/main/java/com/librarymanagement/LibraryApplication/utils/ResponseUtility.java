package com.librarymanagement.LibraryApplication.utils;


import com.librarymanagement.LibraryApplication.models.responses.DefaultResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseUtility {

    public static ResponseEntity<Object> successResponseWithMessageAndBody(String statusCode,
                                                                           String message,
                                                                           Object responseBody,
                                                                           HttpStatus httpStatus) {
        DefaultResponse defaultResponse =
                new DefaultResponse(true, statusCode, message, responseBody);
        return new ResponseEntity<>(defaultResponse, httpStatus);


    }

    public static ResponseEntity<Object> failureResponseWithMessageAndBody(String statusCode,
                                                                           String message,
                                                                           Object responseBody,
                                                                           HttpStatus httpStatus) {
        DefaultResponse defaultResponse =
                new DefaultResponse(false, statusCode, message, responseBody);
        return new ResponseEntity<>(defaultResponse, httpStatus);
    }

    public static ResponseEntity<Object> successResponseWithMessage(String statusCode,
                                                                    String message,
                                                                    HttpStatus httpStatus) {
        DefaultResponse defaultResponse = new DefaultResponse(true, message, statusCode);
        return new ResponseEntity<>(defaultResponse, httpStatus);

    }

    public static ResponseEntity<Object> failureResponseWithMessage(String message,
                                                                    String statusCode,
                                                                    HttpStatus httpStatus) {
        DefaultResponse defaultResponse = new DefaultResponse(false, statusCode, message);
        return new ResponseEntity<>(defaultResponse, httpStatus);
    }


    public static ResponseEntity<Object> authenticationSuccessWithMessage(String statusCode,
                                                                          String message,
                                                                          HttpStatus httpStatus) {
        DefaultResponse authenticationResponse = new DefaultResponse(true, message, statusCode);
        return new ResponseEntity<>(authenticationResponse, httpStatus);
    }


}
