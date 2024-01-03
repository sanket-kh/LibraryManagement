package com.librarymanagement.LibraryApplication.controllers;

import com.librarymanagement.LibraryApplication.models.requests.AccountTypeRequest;
import com.librarymanagement.LibraryApplication.services.AccountTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RestControllerAdvice
@RequiredArgsConstructor
@RequestMapping("api/v1/librarian/account-type")
public class AccountTypeController {
private final AccountTypeService accountTypeService;

@GetMapping("/get-all")
public ResponseEntity<Object> getAllAccountTypesList(){
   return accountTypeService.getAllAccountTypes();
}
@PostMapping("/add")
public ResponseEntity<Object> addAccountType(@RequestBody AccountTypeRequest accountTypeRequest){
    return accountTypeService.addAccountType(accountTypeRequest);
}
}
