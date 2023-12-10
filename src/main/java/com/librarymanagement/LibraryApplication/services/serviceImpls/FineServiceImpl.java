package com.librarymanagement.LibraryApplication.services.serviceImpls;

import ch.qos.logback.core.testUtil.RandomUtil;
import com.library.fine.Dtos.FineCalculationRequest;
import com.library.fine.Dtos.FineDto;
import com.library.fine.Dtos.FinePaymentRequest;
import com.library.fine.responses.LibraryResponse;
import com.library.fine.services.LibService;
import com.librarymanagement.LibraryApplication.models.dtos.finedto.UserFineDto;
import com.librarymanagement.LibraryApplication.models.requests.PaymentRequest;
import com.librarymanagement.LibraryApplication.repositories.FineRepo;
import com.librarymanagement.LibraryApplication.services.FineService;
import com.librarymanagement.LibraryApplication.utils.Constants;
import com.librarymanagement.LibraryApplication.utils.ResponseConstants;
import com.librarymanagement.LibraryApplication.utils.ResponseUtility;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@Transactional
@AllArgsConstructor
public class FineServiceImpl implements FineService {

    private LibService fineService;
    private FineRepo fineRepo;


    @Override
    public LibraryResponse calculateFine(FineCalculationRequest fineCalculationRequest) {
        return fineService.setFine(fineCalculationRequest);
    }

    @Override
    public ResponseEntity<Object> payFine(PaymentRequest paymentRequest) {
        Integer owedAmount = fineRepo.getTotalFineOwedByUser(paymentRequest.getUsername());
        FinePaymentRequest finePaymentRequest = new FinePaymentRequest();
        finePaymentRequest.setAmount(owedAmount);
        finePaymentRequest.setIsPaid(Boolean.FALSE);
        finePaymentRequest.setOverDue(owedAmount/Constants.FINE_PER_DAY);
        LibraryResponse libraryResponse = fineService.payFine(finePaymentRequest);
        if(libraryResponse.getResponseBody()==null){
            return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.OK, libraryResponse.getMessage()), HttpStatus.OK);
        }
        fineRepo.clearFinesForUsername(paymentRequest.getUsername());
        return new ResponseEntity<>(ResponseUtility.successResponseWithMessage(ResponseConstants.OK, libraryResponse.getMessage()), HttpStatus.OK);

    }
}
