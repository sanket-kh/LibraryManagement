package com.librarymanagement.LibraryApplication.models.responses;

public class DefaultResponse {
    private boolean success;
    private String responseCode;
    private String message;
    private Object responseBody;

    public DefaultResponse() {
    }

    public DefaultResponse(boolean success, String message, String responseCode, Object responseBody) {
        this.success = success;
        this.message = message;
        this.responseCode = responseCode;
        this.responseBody = responseBody;
    }

    public DefaultResponse(boolean success, String message, String responseCode) {
        this.success = success;
        this.message = message;
        this.responseCode = responseCode;
    }

    public DefaultResponse(boolean success, String responseCode, Object responseBody) {
        this.success = success;
        this.responseCode = responseCode;
        this.responseBody = responseBody;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(Object responseBody) {
        this.responseBody = responseBody;
    }
}
