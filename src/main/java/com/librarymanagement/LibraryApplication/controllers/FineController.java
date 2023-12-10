package com.librarymanagement.LibraryApplication.controllers;

import com.library.fine.Dtos.FinePaymentRequest;
import com.librarymanagement.LibraryApplication.models.requests.PaymentRequest;
import com.librarymanagement.LibraryApplication.services.FineService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RestControllerAdvice
@AllArgsConstructor
@RequestMapping(value = "api/v1/fine")
public class FineController {
    private FineService fineService;

    @PostMapping("/pay")
    public ResponseEntity<Object> payFine(@RequestBody PaymentRequest paymentRequest){
    return fineService.payFine(paymentRequest);
    }
}
