package com.librarymanagement.LibraryApplication.models.responses;

public record DefaultResponse(boolean success, String responseCode, String message,
                              Object responseBody) {

    public DefaultResponse(boolean success,String responseCode, String message ) {
        this(success, message, responseCode, null);
    }

    public DefaultResponse(boolean success, String responseCode, Object responseBody) {
        this(success, responseCode, null, responseBody);

    }

}
