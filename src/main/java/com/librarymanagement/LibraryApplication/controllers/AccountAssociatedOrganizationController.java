package com.librarymanagement.LibraryApplication.controllers;

import com.librarymanagement.LibraryApplication.models.requests.AccountAssociatedOrganizationRequest;
import com.librarymanagement.LibraryApplication.services.AccountAssociatedOrganizationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RestControllerAdvice
@RequiredArgsConstructor
@RequestMapping("/api/v1/librarian/account-associated-org")

public class AccountAssociatedOrganizationController {
    private final AccountAssociatedOrganizationService accountAssociatedOrganizationService;
    @PostMapping("/add")
    public ResponseEntity<Object> addNewAccountAssociatedOrganization(@RequestBody AccountAssociatedOrganizationRequest associatedOrganizationRequest){
        return accountAssociatedOrganizationService.addAccountAssociatedOrganization(associatedOrganizationRequest);
    }

    @GetMapping("/get")
    public ResponseEntity<Object> getListOfAssociatedOrganizationByType(@RequestParam String accountTypeName){
        return accountAssociatedOrganizationService.getAccountAssociatedOrganizationByAccountType(accountTypeName);
    }

}
