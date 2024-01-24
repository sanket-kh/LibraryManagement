package com.librarymanagement.LibraryApplication.controllers;

import com.librarymanagement.LibraryApplication.models.requests.PaymentRequest;
import com.librarymanagement.LibraryApplication.services.FineService;
import com.librarymanagement.LibraryApplication.utils.Constants;
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
@RequestMapping(value = "api/v1/fine")
public class FineController {
    private final FineService fineService;


    @PostMapping("/pay")
    public ResponseEntity<Object> payFine(@RequestBody PaymentRequest paymentRequest) {
        try {
            return fineService.payFine(paymentRequest);
        } catch (Exception e) {
            log.error("FineController :: payFine", e);
            return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR, "Server Error"), HttpStatus.OK);

        }
    }

    @GetMapping("/user/owed")
    public ResponseEntity<Object> getFinesOwedByUser() {
        try {
            return fineService.getUserFinesList();
        } catch (Exception e) {
            log.error("FineController :: getFinesOwedByUser ", e);
            return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR, "Server Error"), HttpStatus.OK);

        }
    }
    @GetMapping("/owed")
    public ResponseEntity<Object> getFinesOwedByUser(@RequestParam String username) {
        try {
            return fineService.getFinesOwedByUser(username);
        } catch (Exception e) {
            log.error("FineController :: getFinesOwedByUser ", e);
            return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR, "Server Error"), HttpStatus.OK);

        }
    }

    @GetMapping("/get-all")
    public ResponseEntity<Object> getAllFinesOwed(@RequestParam(defaultValue = "5" ) Integer pageSize ,
                                                  @RequestParam(defaultValue = "0") Integer pageNo) {
        try {
            return fineService.getAllFinesList(pageSize, pageNo);
        }catch (Exception e){
            log.error("FineController :: getAllFinesOwed ", e);
            return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR, "Server Error"), HttpStatus.OK);

        }
    }
    @GetMapping("/get-all/unpaid")
    public ResponseEntity<Object> getAllUnpaidFines(@RequestParam(defaultValue ="10") Integer pageSize ,
                                                  @RequestParam(defaultValue = "0") Integer pageNo) {
        try {
            return fineService.getAllUnpaidFines(Constants.PAGE_SIZE, pageNo);
        }catch (Exception e){
            log.error("FineController :: getAllUnpaidFines ", e);
            return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR, "Server Error"), HttpStatus.OK);
        }
    }

}
