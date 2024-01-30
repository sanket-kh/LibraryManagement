package com.librarymanagement.LibraryApplication.controllers;

import com.librarymanagement.LibraryApplication.models.requests.AccountTypeRequest;
import com.librarymanagement.LibraryApplication.services.AccountTypeService;
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
@RequestMapping("api/v1/librarian/account-type")
public class AccountTypeController {
    private final AccountTypeService accountTypeService;

    @GetMapping("/get-all")
    public ResponseEntity<Object> getAllAccountTypesList() {
        try {
            return accountTypeService.getAllAccountTypes();
        } catch (Exception e) {
            log.error("AccountTypeController " +
                      ":: getAllAccountTypesList", e);
            return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR, "Some error occurred"), HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @PostMapping("/add")
    public ResponseEntity<Object> addAccountType(@RequestBody AccountTypeRequest accountTypeRequest) {
        try {
            return accountTypeService.addAccountType(accountTypeRequest);
        } catch (Exception e) {
            log.error("AccountTypeController " +
                      ":: addAccountType", e);
            return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR, "Some error occurred"), HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }
}
