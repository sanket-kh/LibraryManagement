package com.librarymanagement.LibraryApplication.controllers;

import com.librarymanagement.LibraryApplication.services.ReportingStatisticService;
import com.librarymanagement.LibraryApplication.utils.Constants;
import com.librarymanagement.LibraryApplication.utils.ResponseConstants;
import com.librarymanagement.LibraryApplication.utils.ResponseUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Log4j2
@RestController
@RequiredArgsConstructor
@RestControllerAdvice
@RequestMapping("api/v1/report")
public class ReportingStatisticController {
    private final ReportingStatisticService reportingStatisticService;

    @GetMapping("/all-books")
    public ResponseEntity<Object> getListOfAllBooks() {
        try {
            return reportingStatisticService.getListOfAllBooks();
        } catch (Exception e) {
            log.error("ReportingStatisticController :: getListOfAllBooks", e);
            return ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR,
                    Constants.EXCEPTION_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/available-books")
    public ResponseEntity<Object> getListOfAllAvailableBooks() {
        try {
            return reportingStatisticService.getListOfAllAvailableBooks();
        } catch (Exception e) {
            log.error("ReportingStatisticController :: getListOfAllAvailableBooks", e);
            return ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR,
                    Constants.EXCEPTION_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/reserved-books")
    public ResponseEntity<Object> getListOfAllReservedBooks() {
        try {
            return reportingStatisticService.getListOfAllReservedBooks();
        } catch (Exception e) {
            log.error("ReportingStatisticController :: getListOfAllReservedBooks", e);
            return ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR,
                    Constants.EXCEPTION_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/book-count/available")
    public ResponseEntity<Object> getCountOfAvailableBooks() {
        try {
            return reportingStatisticService.getCountOfAvailableBooks();
        } catch (Exception e) {
            log.error("ReportingStatisticController :: getCountOfAvailableBooks", e);
            return ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR,
                    Constants.EXCEPTION_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/book-count/total")
    public ResponseEntity<Object> getCountOfTotalBooks() {
        try {
            return reportingStatisticService.getCountOfTotalBooks();
        } catch (Exception e) {
            log.error("ReportingStatisticController :: getCountOfTotalBooks", e);
            return ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR,
                    Constants.EXCEPTION_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/book/unique/count")
    public ResponseEntity<Object> getBookCount() {
        try {
            return reportingStatisticService.getBookCount();
        } catch (Exception e) {
            log.error("ReportingStatisticController :: getBookCount", e);
            return ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR,
                    Constants.EXCEPTION_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/borrowed-books/total/count")
    public ResponseEntity<Object> getCountOfTotalBorrowedBooks() {
        try {
            return reportingStatisticService.getCountOfTotalBorrowedBooks();
        } catch (Exception e) {
            log.error("ReportingStatisticController :: getCountOfTotalBorrowedBooks", e);
            return ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR,
                    Constants.EXCEPTION_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/borrowed-books/unique/count")
    public ResponseEntity<Object> getCountOfDifferentBorrowedBooks() {
        try {
            return reportingStatisticService.getCountOfUniqueBorrowedBooks();
        } catch (Exception e) {
            log.error("ReportingStatisticController :: getCountOfDifferentBorrowedBooks", e);
            return ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR,
                    Constants.EXCEPTION_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users/total/count")
    public ResponseEntity<Object> getCountOfUser() {
        try {
            return reportingStatisticService.getCountOfUser();
        } catch (Exception e) {
            log.error("ReportingStatisticController :: getCountOfUser", e);
            return ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR,
                    Constants.EXCEPTION_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users/active/count")
    public ResponseEntity<Object> getCountOfActiveUser() {
        try {
            return reportingStatisticService.getCountOfActiveUser();
        } catch (Exception e) {
            log.error("ReportingStatisticController :: getCountOfActiveUser", e);
            return ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR,
                    Constants.EXCEPTION_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users/locked/count")
    public ResponseEntity<Object> getCountOfLockedUser() {
        try {
            return reportingStatisticService.getCountOfLockedUser();
        } catch (Exception e) {
            log.error("ReportingStatisticController :: getCountOfLockedUser", e);
            return ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR,
                    Constants.EXCEPTION_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users/disabled/count")
    public ResponseEntity<Object> getCountOfDisabledUser() {
        try {
            return reportingStatisticService.getCountOfDisabledUser();
        } catch (Exception e) {
            log.error("ReportingStatisticController :: getCountOfDisabledUser", e);
            return ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR,
                    Constants.EXCEPTION_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
