package com.librarymanagement.LibraryApplication.services;

import com.librarymanagement.LibraryApplication.models.requests.AccountTypeRequest;
import org.springframework.http.ResponseEntity;

public interface AccountTypeService {
    ResponseEntity<Object> addAccountType(AccountTypeRequest accountTypeRequest);
    ResponseEntity<Object> getAllAccountTypes();
}
