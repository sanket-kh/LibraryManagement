package com.librarymanagement.LibraryApplication.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.librarymanagement.LibraryApplication.models.responses.AuthenticationResponse;
import com.librarymanagement.LibraryApplication.models.responses.DefaultResponse;
import com.librarymanagement.LibraryApplication.utils.ResponseConstants;
import com.librarymanagement.LibraryApplication.utils.ResponseUtility;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPont implements AuthenticationEntryPoint {
    private final UserDetailsService userDetailsService;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        AuthenticationResponse re =
                ResponseUtility.authenticationFailureWithMessage(ResponseConstants.UN_AUTHORIZED,
                        "Authentication failed. Bad credentials");

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        OutputStream responseStream = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(responseStream, re);
        responseStream.flush();
    }
}
