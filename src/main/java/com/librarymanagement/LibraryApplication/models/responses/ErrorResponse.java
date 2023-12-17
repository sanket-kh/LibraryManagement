package com.librarymanagement.LibraryApplication.models.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {
    private Boolean success;
    private String code;
    private String message;


}
