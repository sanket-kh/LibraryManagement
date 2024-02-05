package com.librarymanagement.LibraryApplication.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.librarymanagement.LibraryApplication.models.responses.DefaultResponse;
import com.librarymanagement.LibraryApplication.utils.ResponseConstants;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;
import java.io.OutputStream;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        DefaultResponse re =
                new DefaultResponse(false,
                        "Access Denied. User doesnt have required authority",
                        ResponseConstants.FORBIDDEN);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        OutputStream responseStream = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(responseStream, re);
        responseStream.flush();
    }
}
