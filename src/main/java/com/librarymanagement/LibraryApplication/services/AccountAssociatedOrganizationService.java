package com.librarymanagement.LibraryApplication.services;

import com.librarymanagement.LibraryApplication.models.requests.AccountAssociatedOrganizationRequest;
import org.springframework.http.ResponseEntity;


public interface AccountAssociatedOrganizationService {
    ResponseEntity<Object> addAccountAssociatedOrganization(AccountAssociatedOrganizationRequest associatedOrganizationRequest);
    ResponseEntity<Object> getAccountAssociatedOrganizationByAccountType(String accountTypeName);

}
