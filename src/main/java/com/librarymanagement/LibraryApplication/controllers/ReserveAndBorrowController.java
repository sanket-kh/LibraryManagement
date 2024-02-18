package com.librarymanagement.LibraryApplication.controllers;

import com.librarymanagement.LibraryApplication.models.dtos.BookTransactionsDto;
import com.librarymanagement.LibraryApplication.models.requests.BorrowRequest;
import com.librarymanagement.LibraryApplication.models.requests.TransactionSearchReq;
import com.librarymanagement.LibraryApplication.models.responses.DefaultResponse;
import com.librarymanagement.LibraryApplication.services.ReserveAndBorrowService;
import com.librarymanagement.LibraryApplication.utils.Constants;
import com.librarymanagement.LibraryApplication.utils.ResponseConstants;
import com.librarymanagement.LibraryApplication.utils.ResponseUtility;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(summary = "Borrow book", responses = {
            @ApiResponse(responseCode = ResponseConstants.OK, description =
                    "Borrowed " + "book " + "successfully", content = @Content(mediaType =
                    "application/json", array = @ArraySchema(schema = @Schema(implementation =
                    DefaultResponse.class)))),
            @ApiResponse(responseCode = ResponseConstants.SERVER_ERROR, description =
                    "Internal " + "Server " + "Error", content = @Content(mediaType =
                    "application/json", schema = @Schema(implementation = DefaultResponse.class))),
            @ApiResponse(responseCode = ResponseConstants.NOT_FOUND, description = "No " + "book "
                                                                                   + "found" + " "
                                                                                   + "for " +
                                                                                   "given "+
                                                                                   "isbn", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DefaultResponse.class))),

    })
    public ResponseEntity<Object> burrowBook(@RequestBody BorrowRequest borrowRequest) {
        try {
            return reserveAndBorrowService.burrowBook(borrowRequest);
        } catch (Exception e) {
            log.error("ReserveAndBorrowController :: burrowBook", e);
            return ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR,
                    "Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/return")
    public ResponseEntity<Object> returnBook(@RequestBody BorrowRequest returnRequest) {
        try {
            return reserveAndBorrowService.returnBook(returnRequest);
        } catch (Exception e) {
            log.error("ReserveAndBorrowController :: returnBook", e);
            return ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR,
                    "Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user/borrowed-books")
    public ResponseEntity<Object> getBorrowedBooksByUser(@RequestParam String username) {
        try {
            return reserveAndBorrowService.viewBurrowedBooksByUser(username);
        } catch (Exception e) {
            log.error("ReserveAndBorrowController :: getBorrowedBooksByUser", e);
            return ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR,
                    "Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




    @GetMapping("/user/transactions")
    public ResponseEntity<Object> getTransactionByUsername(@RequestParam String username,
                                                           @RequestParam(defaultValue =
                                                                   Constants.DEFAULT_PAGE_SIZE) Integer pageSize,
                                                           @RequestParam(defaultValue = "0") Integer pageNo) {
        try {
            return reserveAndBorrowService.getUserTransaction(username, pageSize, pageNo);
        } catch (Exception e) {
            log.error("ReserveAndBorrowController :: getTransactionByUsername");
            return ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR,
                    "Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/transactions/get-all")
    @Operation(summary = "get a list of associated organization by type", responses = {
            @ApiResponse(responseCode = ResponseConstants.OK, description = "successful " +
                                                                            "operation", content
                    = @Content(mediaType = "application/json", array = @ArraySchema(schema =
            @Schema(implementation = BookTransactionsDto.class)))),
            @ApiResponse(responseCode = ResponseConstants.SERVER_ERROR, description =
                    "Internal " + "Server " + "Error", content = @Content(mediaType =
                    "application/json", schema = @Schema(implementation = DefaultResponse.class))),
            @ApiResponse(responseCode = ResponseConstants.NOT_FOUND, description = "transactions "
                                                                                   + "not found",
                    content = @Content(mediaType = "application/json", schema =
                    @Schema(implementation = DefaultResponse.class))),

    })
    public ResponseEntity<Object> getAllTransactions(
            @RequestParam(defaultValue = Constants.DEFAULT_PAGE_SIZE) Integer pageSize,
            @RequestParam(defaultValue = "0") Integer pageNo) {
        try {
            return reserveAndBorrowService.getAllTransactions(pageNo, pageSize);
        } catch (Exception e) {
            log.error("ReserveAndBorrowController :: getAllTransactions");
            return ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR,
                    "Server Error", HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    @PostMapping("/transaction/search")
    public ResponseEntity<Object> searchTransactions(
            @RequestBody TransactionSearchReq transactionSearchReq) {
        try {
            return reserveAndBorrowService.searchTransactions(transactionSearchReq);
        } catch (Exception e) {
            log.error("ReserveAndBorrowController :: searchTransactions");
            return ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR,
                    "Server Error", HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }
}
