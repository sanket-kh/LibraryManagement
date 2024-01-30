package com.librarymanagement.LibraryApplication.controllers;

import com.librarymanagement.LibraryApplication.models.requests.AccountAssociatedOrganizationRequest;
import com.librarymanagement.LibraryApplication.services.AccountAssociatedOrganizationService;
import com.librarymanagement.LibraryApplication.utils.ResponseConstants;
import com.librarymanagement.LibraryApplication.utils.ResponseUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
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
        try {
            return accountAssociatedOrganizationService.addAccountAssociatedOrganization(associatedOrganizationRequest);
        } catch (Exception e) {
            log.error("AccountAssociatedOrganizationController " +
                      ":: addNewAccountAssociatedOrganization", e);
            return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR, "Some error occurred"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get")
    public ResponseEntity<Object> getListOfAssociatedOrganizationByType(@RequestParam String accountTypeName){
        try {
            return accountAssociatedOrganizationService.getAccountAssociatedOrganizationByAccountType(accountTypeName);
        } catch (Exception e) {
            log.error("AccountAssociatedOrganizationController " +
                      ":: getListOfAssociatedOrganizationByType",e);
            return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR, "Some error occurred"), HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

}
