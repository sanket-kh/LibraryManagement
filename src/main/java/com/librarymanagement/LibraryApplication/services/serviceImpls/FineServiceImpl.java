package com.librarymanagement.LibraryApplication.services.serviceImpls;

import com.library.fine.Dtos.FineCalculationRequest;
import com.library.fine.Dtos.FinePaymentRequest;
import com.library.fine.responses.LibraryResponse;
import com.library.fine.services.LibService;
import com.librarymanagement.LibraryApplication.models.dtos.UserFineDto;
import com.librarymanagement.LibraryApplication.models.requests.PaymentRequest;
import com.librarymanagement.LibraryApplication.repositories.FineRepo;
import com.librarymanagement.LibraryApplication.services.FineService;
import com.librarymanagement.LibraryApplication.utils.Constants;
import com.librarymanagement.LibraryApplication.utils.ResponseConstants;
import com.librarymanagement.LibraryApplication.utils.ResponseUtility;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional(rollbackOn = Exception.class)
public class FineServiceImpl implements FineService {

    private final LibService fineService;
    private final FineRepo fineRepo;


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
        finePaymentRequest.setOverDue(owedAmount / Constants.FINE_PER_DAY);
        LibraryResponse libraryResponse = fineService.payFine(finePaymentRequest);
        if (libraryResponse.getResponseBody() == null) {
            return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.INSUFFICIENT_FUND, libraryResponse.getMessage()), HttpStatus.CONFLICT);
        }
        fineRepo.clearFinesForUsername(paymentRequest.getUsername());
        return new ResponseEntity<>(ResponseUtility.successResponseWithMessage(ResponseConstants.OK, libraryResponse.getMessage()), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<Object> getUserFinesList() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        String username = authentication.getName();
        List<UserFineDto> userFineDtos = fineRepo.getFinesByUsername(username);
        if(userFineDtos.isEmpty()){
            return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.NO_CONTENT, "No fines owed by user"), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(ResponseUtility.successResponseWithMessageAndBody(ResponseConstants.OK, "Fines owed by user retrieved", userFineDtos), HttpStatus.OK);
    }
}
