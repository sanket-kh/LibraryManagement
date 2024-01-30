package com.librarymanagement.LibraryApplication.services.serviceImpls;

import com.librarymanagement.LibraryApplication.entities.AccountType;
import com.librarymanagement.LibraryApplication.mappers.AccountTypeMapper;
import com.librarymanagement.LibraryApplication.models.requests.AccountTypeRequest;
import com.librarymanagement.LibraryApplication.repositories.AccountTypeRepo;
import com.librarymanagement.LibraryApplication.services.AccountTypeService;
import com.librarymanagement.LibraryApplication.utils.ResponseConstants;
import com.librarymanagement.LibraryApplication.utils.ResponseUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
@Log4j2
@Service
@RequiredArgsConstructor
public class AccountTypeServiceImpl implements AccountTypeService {
    private final AccountTypeRepo accountTypeRepo;

    @Override
    public ResponseEntity<Object> addAccountType(AccountTypeRequest accountTypeRequest) {
        try {
            AccountType accountType = accountTypeRepo.findAccountTypeByAccountTypeNameIgnoreCase(accountTypeRequest.getAccountTypeName());
            if (accountType == null) {
                accountType = new AccountType();
                accountType.setAccountTypeName(accountTypeRequest.getAccountTypeName());
                accountTypeRepo.save(accountType);
                return new ResponseEntity<>(ResponseUtility.successResponseWithMessage(ResponseConstants.OK, "Account type added"), HttpStatus.OK);
            }
            return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.ALREADY_EXISTS, "Account type " + "already exist"), HttpStatus.CONFLICT);
        } catch (Exception e) {
            log.error("AccountTypeService :: addAccountType",e);
            return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.INTERNAL_ERROR, "Some exception occurred"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Object> getAllAccountTypes() {
        try {
            List<AccountType> accountTypeList = accountTypeRepo.findAllAccountTypes();
            if (accountTypeList.isEmpty()) {
                return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.NO_CONTENT, "Account type list is empty"), HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(ResponseUtility.successResponseWithMessageAndBody(ResponseConstants.OK, "Account type list retrieved", AccountTypeMapper.mapToListOfAccountTypeName(accountTypeList)), HttpStatus.OK);
        } catch (Exception e) {
            log.error("AccountTypeService :: getAllAccountTypes",e);
            return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.INTERNAL_ERROR, "Some exception occurred"), HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

}
