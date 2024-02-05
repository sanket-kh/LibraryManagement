package com.librarymanagement.LibraryApplication.utils;


import com.librarymanagement.LibraryApplication.models.responses.DefaultResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtility {

    public static ResponseEntity<Object> successResponseWithMessageAndBody(String statusCode,
                                                                           String message,
                                                                           Object responseBody,
                                                                           HttpStatus httpStatus) {
        DefaultResponse defaultResponse = new DefaultResponse();
        defaultResponse.setSuccess(true);
        defaultResponse.setResponseBody(responseBody);
        defaultResponse.setMessage(message);
        defaultResponse.setResponseCode(statusCode);

        return new ResponseEntity<>(defaultResponse, httpStatus);


    }

    public static ResponseEntity<Object> failureResponseWithMessageAndBody(String statusCode,
                                                                           String message,
                                                                           Object responseBody,
                                                                           HttpStatus httpStatus) {
        DefaultResponse defaultResponse = new DefaultResponse();
        defaultResponse.setSuccess(false);
        defaultResponse.setResponseBody(responseBody);
        defaultResponse.setMessage(message);
        defaultResponse.setResponseCode(statusCode);
        return new ResponseEntity<>(defaultResponse, httpStatus);
    }

    public static ResponseEntity<Object> successResponseWithMessage(String statusCode,
                                                                    String message, HttpStatus httpStatus) {
        DefaultResponse defaultResponse = new DefaultResponse();
        defaultResponse.setSuccess(true);
        defaultResponse.setMessage(message);
        defaultResponse.setResponseCode(statusCode);
        return new ResponseEntity<>(defaultResponse, httpStatus);
        //implement RESPONSE ENTITY HERE
    }

    public static ResponseEntity<Object> failureResponseWithMessage(String statusCode, String message,
                                                                    HttpStatus httpStatus) {
        DefaultResponse defaultResponse = new DefaultResponse();
        defaultResponse.setSuccess(false);
        defaultResponse.setMessage(message);
        defaultResponse.setResponseCode(statusCode);
        return new ResponseEntity<>(defaultResponse, httpStatus);
    }


    public static ResponseEntity<Object> authenticationSuccessWithMessage(String statusCode,
                                                                   String message, HttpStatus httpStatus) {
        DefaultResponse authenticationResponse = new DefaultResponse();
        authenticationResponse.setMessage(message);
        authenticationResponse.setSuccess(Boolean.TRUE);
        authenticationResponse.setResponseCode(statusCode);
        return new ResponseEntity<>(authenticationResponse, httpStatus);
    }


}
