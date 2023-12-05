package com.librarymanagement.LibraryApplication.utils;


import com.librarymanagement.LibraryApplication.models.responses.DefaultResponse;

public class ResponseUtility {
    public static DefaultResponse successResponseWithBody(String statusCode, Object responseBody) {
        DefaultResponse defaultResponse = new DefaultResponse();
        defaultResponse.setSuccess(true);
        defaultResponse.setResponseBody(responseBody);
        defaultResponse.setResponseCode(statusCode);
        return defaultResponse;

    }

    public static DefaultResponse failureResponseWithBody(String statusCode, Object responseBody) {
        DefaultResponse defaultResponse = new DefaultResponse();
        defaultResponse.setSuccess(true);
        defaultResponse.setResponseBody(responseBody);
        defaultResponse.setResponseCode(statusCode);
        return defaultResponse;
    }

    public static DefaultResponse successResponseWithMessageAndBody(String statusCode, String message, Object responseBody) {
        DefaultResponse defaultResponse = new DefaultResponse();
        defaultResponse.setSuccess(true);
        defaultResponse.setResponseBody(responseBody);
        defaultResponse.setMessage(message);
        defaultResponse.setResponseCode(statusCode);
        return defaultResponse;


    }

    public static DefaultResponse failureResponseWithMessageAndBody(String statusCode, String message, Object responseBody) {
        DefaultResponse defaultResponse = new DefaultResponse();
        defaultResponse.setSuccess(false);
        defaultResponse.setResponseBody(responseBody);
        defaultResponse.setMessage(message);
        defaultResponse.setResponseCode(statusCode);
        return defaultResponse;
    }

    public static DefaultResponse successResponseWithMessage(String statusCode, String message) {
        DefaultResponse defaultResponse = new DefaultResponse();
        defaultResponse.setSuccess(true);
        defaultResponse.setMessage(message);
        defaultResponse.setResponseCode(statusCode);
        return defaultResponse;
    }

    public static DefaultResponse failureResponseWithMessage(String statusCode, String message) {
        DefaultResponse defaultResponse = new DefaultResponse();
        defaultResponse.setSuccess(false);
        defaultResponse.setMessage(message);
        defaultResponse.setResponseCode(statusCode);
        return defaultResponse;
    }




}
