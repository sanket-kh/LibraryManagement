package com.librarymanagement.LibraryApplication.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.librarymanagement.LibraryApplication.models.responses.ErrorResponse;
import com.librarymanagement.LibraryApplication.utils.ResponseConstants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.io.OutputStream;

public class ErrorResponseUtil {
    public static void setJwtErrorResponse(HttpServletRequest request,
                                           HttpServletResponse response,
                                           String statusCode,
                                           String message) throws IOException {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(ResponseConstants.JWT_ERROR);
        errorResponse.setMessage("Jwt token has expired");

        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        OutputStream responseStream = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(responseStream, errorResponse);
        responseStream.flush();

    }
}
