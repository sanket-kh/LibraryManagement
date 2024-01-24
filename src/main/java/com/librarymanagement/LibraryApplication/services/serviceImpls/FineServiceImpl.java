package com.librarymanagement.LibraryApplication.services.serviceImpls;

import com.library.fine.Dtos.FineCalculationRequest;
import com.library.fine.Dtos.FinePaymentRequest;
import com.library.fine.responses.LibraryResponse;
import com.library.fine.services.LibService;
import com.librarymanagement.LibraryApplication.entities.Fine;
import com.librarymanagement.LibraryApplication.entities.User;
import com.librarymanagement.LibraryApplication.mappers.FineMapper;
import com.librarymanagement.LibraryApplication.mappers.PageMapper;
import com.librarymanagement.LibraryApplication.models.requests.PaymentRequest;
import com.librarymanagement.LibraryApplication.models.responses.PageResponse;
import com.librarymanagement.LibraryApplication.repositories.FineRepo;
import com.librarymanagement.LibraryApplication.repositories.UserRepo;
import com.librarymanagement.LibraryApplication.services.FineService;
import com.librarymanagement.LibraryApplication.utils.Constants;
import com.librarymanagement.LibraryApplication.utils.ResponseConstants;
import com.librarymanagement.LibraryApplication.utils.ResponseUtility;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    private final UserRepo userRepo;


    @Override
    public LibraryResponse calculateFine(FineCalculationRequest fineCalculationRequest) {
        return fineService.setFine(fineCalculationRequest);
    }

    @Override
    public ResponseEntity<Object> payFine(PaymentRequest paymentRequest) {
        Integer owedAmount = fineRepo.getFineOwedByUserOnBook(paymentRequest.getUsername(),
                paymentRequest.getIsbn());
        FinePaymentRequest finePaymentRequest = new FinePaymentRequest();
        finePaymentRequest.setAmount(owedAmount);
        finePaymentRequest.setIsPaid(Boolean.FALSE);
        finePaymentRequest.setPaidAmount(paymentRequest.getAmount());
        finePaymentRequest.setOverDue(owedAmount / Constants.FINE_PER_DAY);
        LibraryResponse libraryResponse = fineService.payFine(finePaymentRequest);
        if (libraryResponse.getResponseBody() == null) {
            return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.INSUFFICIENT_FUND, libraryResponse.getMessage()), HttpStatus.CONFLICT);
        }
        fineRepo.clearFinesForUsernameOnBook(paymentRequest.getUsername(), paymentRequest.getIsbn());
        return new ResponseEntity<>(ResponseUtility.successResponseWithMessage(ResponseConstants.OK, libraryResponse.getMessage()), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<Object> getUserFinesList() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        String username = authentication.getName();
        User user = userRepo.findUserByUsername(username);
        List<Fine> fines = fineRepo.getAllUnpaidFinesByUser(user);
        if (fines.isEmpty()) {
            return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.NO_CONTENT, "No fines owed by user"), HttpStatus.NOT_FOUND);

        }
        return new ResponseEntity<>(ResponseUtility.successResponseWithMessageAndBody(ResponseConstants.OK, "Fines owed by user retrieved", FineMapper.mapToFinesDto(fines)), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> getAllFinesList(Integer pageSize, Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("id"));
        Page<Fine> fines = fineRepo.findAll(pageable);
        PageResponse pageResponse = PageMapper.mapFinePageToPageResponse(fines);
        return new ResponseEntity<>(ResponseUtility.successResponseWithMessageAndBody(ResponseConstants.OK, "Fines page received", pageResponse), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> getAllUnpaidFines(Integer pageSize, Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("id").descending());
        Page<Fine> fines = fineRepo.getAllUnpaidFinesTransaction(pageable);
        PageResponse pageResponse = PageMapper.mapFinePageToPageResponse(fines);
        return new ResponseEntity<>(ResponseUtility.successResponseWithMessageAndBody(ResponseConstants.OK, "Fines page received", pageResponse), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<Object> getFinesOwedByUser(String username) {
        User user = userRepo.findUserByUsername(username);
        List<Fine> fines = fineRepo.getAllUnpaidFinesByUser(user);
        if (fines.isEmpty()) {
            return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.NO_CONTENT, "No fines owed by user"), HttpStatus.NOT_FOUND);

        }
        return new ResponseEntity<>(ResponseUtility.successResponseWithMessageAndBody(ResponseConstants.OK, "Fines owed by user retrieved", FineMapper.mapToFinesDto(fines)), HttpStatus.OK);

    }
}
