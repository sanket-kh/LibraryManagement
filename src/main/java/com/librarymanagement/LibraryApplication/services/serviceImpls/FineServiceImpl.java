package com.librarymanagement.LibraryApplication.services.serviceImpls;

import com.library.fine.Dtos.FineCalculationRequest;
import com.library.fine.Dtos.FinePaymentRequest;
import com.library.fine.responses.LibraryResponse;
import com.library.fine.services.LibService;
import com.librarymanagement.LibraryApplication.entities.Fine;
import com.librarymanagement.LibraryApplication.services.FineService;
import com.librarymanagement.LibraryApplication.utils.ResponseUtility;
import io.netty.handler.codec.http.HttpResponseStatus;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@Transactional
@AllArgsConstructor
public class FineServiceImpl implements FineService {

    private LibService fineService;


    @Override
    public LibraryResponse calculateFine(FineCalculationRequest fineCalculationRequest) {
      return fineService.setFine(fineCalculationRequest);
    }

    @Override
    public LibraryResponse payFine(FinePaymentRequest finePaymentRequest) {
        return fineService.payFine(finePaymentRequest);
    }

    ;
}
