package com.librarymanagement.LibraryApplication.services;


import com.library.fine.Dtos.FineCalculationRequest;
import com.library.fine.Dtos.FinePaymentRequest;
import com.library.fine.responses.LibraryResponse;

public interface FineService {
    public LibraryResponse calculateFine(FineCalculationRequest fineCalculationRequest);
    public LibraryResponse payFine(FinePaymentRequest finePaymentRequest);
}
