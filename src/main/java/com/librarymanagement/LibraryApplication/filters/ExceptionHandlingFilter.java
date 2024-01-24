package com.librarymanagement.LibraryApplication.filters;

import com.librarymanagement.LibraryApplication.exceptions.NoAuthenticationHeaderException;
import com.librarymanagement.LibraryApplication.utils.ErrorResponseUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

@Component
@Log4j2
public class ExceptionHandlingFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws IOException {

        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        try {
            filterChain.doFilter(requestWrapper, responseWrapper);
            responseWrapper.copyBodyToResponse();
        } catch (ExpiredJwtException e) {
            log.error(e);
            ErrorResponseUtil.setJwtErrorResponse( responseWrapper,"JWT token has expired" );

       }catch (SignatureException e){
            log.error(e);
            ErrorResponseUtil.setJwtErrorResponse(responseWrapper, "Invalid jwt signature");
        }
        catch (JwtException e){
            log.error(e);
           ErrorResponseUtil.setJwtErrorResponse(responseWrapper,"Cant validate JWT token" );
        }catch (NoAuthenticationHeaderException e){
            log.error(e);
            ErrorResponseUtil.setJwtErrorResponse(responseWrapper,"No authentication header");
        }
        catch (Exception ex){
            log.error(ex);
            ex.printStackTrace();
            ErrorResponseUtil.setFatalErrorResponse(responseWrapper,"Exception in the filter chain has " +
                    "occurred");
        }
    }
}
