package com.librarymanagement.LibraryApplication.controllers;

import com.librarymanagement.LibraryApplication.models.dtos.AccountTypesDto;
import com.librarymanagement.LibraryApplication.models.requests.AccountTypeRequest;
import com.librarymanagement.LibraryApplication.models.responses.DefaultResponse;
import com.librarymanagement.LibraryApplication.services.AccountTypeService;
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
@RestControllerAdvice
@RequiredArgsConstructor
@RequestMapping("api/v1/librarian/account-type")
public class AccountTypeController {
    private final AccountTypeService accountTypeService;

    @GetMapping("/get-all")
    @Operation(summary = "get a list of associated organization by type",
            responses = {
                    @ApiResponse(responseCode = ResponseConstants.OK, description = "successful operation",
                            content=@Content(mediaType = "application/json",
                                    array =@ArraySchema(schema =
                                    @Schema(implementation = AccountTypesDto.class)))),
                    @ApiResponse(responseCode = ResponseConstants.SERVER_ERROR, description = "Internal Server Error",
                            content=@Content(mediaType = "application/json",
                                    schema =@Schema(implementation = DefaultResponse.class))),
                    @ApiResponse(responseCode = ResponseConstants.NOT_FOUND, description = "No " +
                                                                                           "organization " +
                                                                                           "found for " +
                                                                                           "account type",
                            content=@Content(mediaType = "application/json",
                                    schema =@Schema(implementation = DefaultResponse.class))),

            })
    public ResponseEntity<Object> getAllAccountTypesList() {
        try {
            return accountTypeService.getAllAccountTypes();
        } catch (Exception e) {
            log.error("AccountTypeController " +
                      ":: getAllAccountTypesList", e);
            return   ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR,
                    "Some error occurred", HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @PostMapping("/add")
    public ResponseEntity<Object> addAccountType(@RequestBody AccountTypeRequest accountTypeRequest) {
        try {
            return accountTypeService.addAccountType(accountTypeRequest);
        } catch (Exception e) {
            log.error("AccountTypeController " +
                      ":: addAccountType", e);
            return   ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR,
                    "Some error occurred", HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }
}
