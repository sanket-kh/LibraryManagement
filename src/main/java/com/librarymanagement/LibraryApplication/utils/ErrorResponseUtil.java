package com.librarymanagement.LibraryApplication.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.librarymanagement.LibraryApplication.models.responses.DefaultResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.io.OutputStream;

public class ErrorResponseUtil {
    public static void setJwtErrorResponse(
                                           ContentCachingResponseWrapper responseWrapper,
                                           String message) throws IOException {
        DefaultResponse defaultResponse = setGeneralErrorResponseObject(ResponseConstants.JWT_ERROR,
                message);
        HttpServletResponse rawResponse = (HttpServletResponse) responseWrapper.getResponse();

        rawResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        rawResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        OutputStream responseStream = rawResponse.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(responseStream, defaultResponse);
        responseStream.flush();
        responseWrapper.copyBodyToResponse();
    }

    public static void setFatalErrorResponse(ContentCachingResponseWrapper responseWrapper, String message) throws IOException {
        DefaultResponse errorResponse = setGeneralErrorResponseObject(ResponseConstants.JWT_ERROR,
                message);
        responseWrapper.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseWrapper.setContentType(MediaType.APPLICATION_JSON_VALUE);
        OutputStream responseStream = responseWrapper.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(responseStream, errorResponse);
        responseStream.flush();
        responseWrapper.copyBodyToResponse();
    }

    public static DefaultResponse setNoHandlerFoundResponse(String message) {
    DefaultResponse errorResponse = new DefaultResponse();
    errorResponse.setSuccess(Boolean.FALSE);
    errorResponse.setResponseCode(ResponseConstants.NOT_FOUND);
    errorResponse.setMessage(message);
    return errorResponse;
    }


    public static DefaultResponse setGeneralErrorResponseObject(String statusCode,
                                                                String message) {
        DefaultResponse errorResponse = new DefaultResponse();
        errorResponse.setMessage(message);
        errorResponse.setResponseCode(statusCode);
        errorResponse.setSuccess(Boolean.FALSE);
        return errorResponse;
    }
}
