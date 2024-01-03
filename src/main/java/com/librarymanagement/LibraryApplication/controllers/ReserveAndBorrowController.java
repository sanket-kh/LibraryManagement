package com.librarymanagement.LibraryApplication.controllers;

import com.librarymanagement.LibraryApplication.models.requests.BorrowRequest;
import com.librarymanagement.LibraryApplication.models.requests.ReserveRequest;
import com.librarymanagement.LibraryApplication.services.ReserveAndBorrowService;
import com.librarymanagement.LibraryApplication.utils.ResponseConstants;
import com.librarymanagement.LibraryApplication.utils.ResponseUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequiredArgsConstructor
@RestControllerAdvice
@RequestMapping("/api/v1")
public class ReserveAndBorrowController {
    private final ReserveAndBorrowService reserveAndBorrowService;

    @PostMapping("/borrow")
    public ResponseEntity<Object> burrowBook(@RequestBody BorrowRequest borrowRequest) {
        try {
            return reserveAndBorrowService.burrowBook(borrowRequest);
        } catch (Exception e) {
            log.error("ReserveAndBorrowController :: burrowBook", e);
            return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR,
                    "Server Error"), HttpStatus.OK);
        }
    }

    @PostMapping("/return")
    public ResponseEntity<Object> returnBook(@RequestBody BorrowRequest returnRequest) {
        try {
            return reserveAndBorrowService.returnBook(returnRequest);
        } catch (Exception e) {
            log.error("ReserveAndBorrowController :: returnBook", e);
            return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR,
                    "Server Error"), HttpStatus.OK);
        }
    }

    @GetMapping("/user/borrowed-books")
    public ResponseEntity<Object> getBorrowedBooksByUser(@RequestParam String username) {
        try {
            return reserveAndBorrowService.viewBurrowedBooksByUser(username);
        } catch (Exception e) {
            log.error("ReserveAndBorrowController :: getBorrowedBooksByUser", e);
            return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR,
                    "Server Error"), HttpStatus.OK);
        }
    }

    @PostMapping("/reserve")
    public ResponseEntity<Object> reserveBook(@RequestBody ReserveRequest reserveRequest) {
        try {
            return reserveAndBorrowService.reserveUnavailableBook(reserveRequest);
        } catch (Exception e) {
            log.error("ReserveAndBorrowController :: reserveBook", e);
            return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR,
                    "Server Error"), HttpStatus.OK);
        }
    }

    @PostMapping("/user/cancel-reserve")
    public ResponseEntity<Object> cancelReservationBook(@RequestBody ReserveRequest cancelReservationRequest) {
        try {
            return reserveAndBorrowService.cancelReservationOfBook(cancelReservationRequest);
        } catch (Exception e) {
            log.error("ReserveAndBorrowController :: cancelReservationBook", e);
            return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR,
                    "Server Error"), HttpStatus.OK);
        }
    }

    @GetMapping("/user/reserved-books")
    public ResponseEntity<Object> getReservedBooksByUser(@RequestParam String username) {
        try {
            return reserveAndBorrowService.viewReservedBooksByUser(username);
        } catch (Exception e) {
            log.error("ReserveAndBorrowController :: getReservedBooksByUser", e);
            return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR,
                    "Server Error"), HttpStatus.OK);
        }
    }
}
