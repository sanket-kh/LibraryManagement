package com.librarymanagement.LibraryApplication.handlers;

import com.librarymanagement.LibraryApplication.utils.ErrorResponseUtil;
import com.librarymanagement.LibraryApplication.utils.ResponseConstants;
import com.librarymanagement.LibraryApplication.utils.ResponseUtility;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
        return new ResponseEntity<>(ResponseUtility.failureResponseWithMessageAndBody(ResponseConstants.BAD_REQUEST,
                "Invalid request parameters", getErrorsMap(errors)),HttpStatus.OK);
    }
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex){
        return new ResponseEntity<>(ErrorResponseUtil.setNoHandlerFoundResponse("Invalid API " +
                "path"), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleMethodArgumentValidationErrors(MethodArgumentTypeMismatchException ex) {
        Map<String , String> errorMap = new HashMap<>();
        errorMap.put(ex.getName(), "expected type: "+ Objects.requireNonNull(ex.getRequiredType()).getSimpleName()+"."+" " +
                        "Provided: "+ex.getValue());

        return new ResponseEntity<>(ResponseUtility.failureResponseWithMessageAndBody(ResponseConstants.BAD_REQUEST,
                "Invalid request parameter", errorMap),HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(WebClientRequestException.class)
    public ResponseEntity<Object> webClientRequestException(WebClientRequestException ex){
        return new ResponseEntity<>(ErrorResponseUtil.setGeneralErrorResponseObject(ResponseConstants.WEB_CLIENT_ERROR,
                "WebClient connection was unsuccessful")
                , HttpStatus.GATEWAY_TIMEOUT);
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> accessDeniedException(AccessDeniedException ex){
        return new ResponseEntity<>(ErrorResponseUtil.setGeneralErrorResponseObject(ResponseConstants.FORBIDDEN,"Access denied. Authorization failed"),HttpStatus.OK);
    }
    private Map<String, List<String>> getErrorsMap(List<String> errors) {
        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("errors", errors);
        return errorResponse;
    }


}
