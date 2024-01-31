package com.librarymanagement.LibraryApplication.controllers;

import com.librarymanagement.LibraryApplication.models.dtos.AccountAssociatedOrganizationDto;
import com.librarymanagement.LibraryApplication.models.dtos.LibrarianAccountDto;
import com.librarymanagement.LibraryApplication.models.requests.LibrarianAccountRequest;
import com.librarymanagement.LibraryApplication.models.responses.DefaultResponse;
import com.librarymanagement.LibraryApplication.services.LibrarianAccountService;
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

import java.util.List;

@Log4j2
@RestController
@RestControllerAdvice
@RequiredArgsConstructor
@RequestMapping("/api/v1/librarian/account")
public class LibrarianAccountController {
    private final LibrarianAccountService librarianAccountService;

    @PostMapping("/add")
    public ResponseEntity<Object> addAccountDetails(@RequestBody List<LibrarianAccountRequest> librarianAccountRequest) {
        try {
            return librarianAccountService.addLibrarianAccount(librarianAccountRequest);
        } catch (Exception e) {
            log.error("LibrarianAccountController::addAccountDetails", e);
            return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR, "Some error occurred"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get")
    @Operation(summary = "get a list of librarian accounts",
            responses = {
                    @ApiResponse(responseCode = ResponseConstants.OK, description = "successful operation",
                            content=@Content(mediaType = "application/json",
                                    array =@ArraySchema(schema =
                                    @Schema(implementation = LibrarianAccountDto.class)))),
                    @ApiResponse(responseCode = ResponseConstants.SERVER_ERROR, description = "Internal Server Error",
                            content=@Content(mediaType = "application/json",
                                    schema =@Schema(implementation = DefaultResponse.class))),
                    @ApiResponse(responseCode = ResponseConstants.NOT_FOUND, description =
                            "Account details not found",
                            content=@Content(mediaType = "application/json",
                                    schema =@Schema(implementation = DefaultResponse.class))),

            })
    public ResponseEntity<Object> getAccountDetails() {
        try {
            return librarianAccountService.getDetails();
        } catch (Exception e) {
            log.error("LibrarianAccountController :: getAccountDetails", e);
            return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR, "Some error occurred"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/update")
    public ResponseEntity<Object> modifyAccountDetails(@RequestBody List<LibrarianAccountRequest> librarianAccountRequest) {
        try {
            return librarianAccountService.modifyDetails(librarianAccountRequest);
        } catch (Exception e) {
            log.error("LibrarianAccountController :: modifyAccountDetails", e);
            return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR, "Some error occurred"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/clear")
    public ResponseEntity<Object> clearAccountDetails(){
        try {
            return librarianAccountService.makeAccDetailsInactive();
        } catch (Exception e) {
            log.error("LibrarianAccountController :: clearAccountDetails", e);
            return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR, "Some error occurred"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/fine-account/get")
    public ResponseEntity<Object> getFineClearAccountByAccountType(@RequestParam String accountType){
        try {
            return librarianAccountService.getClearFineAccountByAccountType(accountType);
        } catch (Exception e) {
            log.error("LibrarianAccountController :: getFineClearAccountByAccountType", e);
            return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR, "Some error occurred"), HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

}
