package com.librarymanagement.LibraryApplication.models.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentRequest {
    private String username;
    private Integer amount;
}
