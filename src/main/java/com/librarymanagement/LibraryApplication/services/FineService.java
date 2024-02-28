package com.librarymanagement.LibraryApplication.services;


import com.library.fine.Dtos.FineCalculationRequest;
import com.library.fine.responses.LibraryResponse;
import com.librarymanagement.LibraryApplication.models.requests.PaymentRequest;
import org.springframework.http.ResponseEntity;

public interface FineService {
    LibraryResponse calculateFine(FineCalculationRequest fineCalculationRequest);

    ResponseEntity<Object> payFine(PaymentRequest paymentRequest);

    ResponseEntity<Object> getUserFinesList();

    ResponseEntity<Object> getAllFinesList(Integer pageSize, Integer pageNo);

    ResponseEntity<Object> getAllUnpaidFines(Integer pageSize, Integer pageNo);

    ResponseEntity<Object> getFinesOwedByUser(String username);
}
