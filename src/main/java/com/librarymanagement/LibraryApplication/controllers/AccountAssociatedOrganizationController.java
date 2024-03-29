package com.librarymanagement.LibraryApplication.controllers;

import com.librarymanagement.LibraryApplication.models.dtos.AccountAssociatedOrganizationDto;
import com.librarymanagement.LibraryApplication.models.requests.AccountAssociatedOrganizationRequest;
import com.librarymanagement.LibraryApplication.models.responses.DefaultResponse;
import com.librarymanagement.LibraryApplication.services.AccountAssociatedOrganizationService;
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
@RequestMapping("/api/v1/librarian/account-associated-org")


public class AccountAssociatedOrganizationController {
    private final AccountAssociatedOrganizationService accountAssociatedOrganizationService;
    @PostMapping("/add")
    public ResponseEntity<Object> addNewAccountAssociatedOrganization(@RequestBody AccountAssociatedOrganizationRequest associatedOrganizationRequest){
        try {
            return accountAssociatedOrganizationService.addAccountAssociatedOrganization(associatedOrganizationRequest);
        } catch (Exception e) {
            log.error("AccountAssociatedOrganizationController " +
                      ":: addNewAccountAssociatedOrganization", e);
            return   ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR,
                    "Some error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get")
    @Operation(summary = "get a list of associated organization by type",
    responses = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content=@Content(mediaType="application/json",
                            array = @ArraySchema(schema = @Schema(implementation = AccountAssociatedOrganizationDto.class)))),
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
    public ResponseEntity<Object> getListOfAssociatedOrganizationByType(@RequestParam String accountTypeName){
        try {
            return accountAssociatedOrganizationService.getAccountAssociatedOrganizationByAccountType(accountTypeName);
        } catch (Exception e) {
            log.error("AccountAssociatedOrganizationController " +
                      ":: getListOfAssociatedOrganizationByType",e);
            return   ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR,
                    "Some error occurred", HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

}
