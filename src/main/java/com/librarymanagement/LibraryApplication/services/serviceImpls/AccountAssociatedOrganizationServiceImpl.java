package com.librarymanagement.LibraryApplication.services.serviceImpls;

import com.librarymanagement.LibraryApplication.entities.AccountAssociatedOrganization;
import com.librarymanagement.LibraryApplication.entities.AccountType;
import com.librarymanagement.LibraryApplication.models.requests.AccountAssociatedOrganizationRequest;
import com.librarymanagement.LibraryApplication.repositories.AccountAssociatedOrganizationRepo;
import com.librarymanagement.LibraryApplication.repositories.AccountTypeRepo;
import com.librarymanagement.LibraryApplication.services.AccountAssociatedOrganizationService;
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

public class AccountAssociatedOrganizationServiceImpl implements AccountAssociatedOrganizationService {
 private final AccountTypeRepo accountTypeRepo;
 private final AccountAssociatedOrganizationRepo accountAssociatedOrganizationRepo;
    @Override
    public ResponseEntity<Object> addAccountAssociatedOrganization(AccountAssociatedOrganizationRequest associatedOrganizationRequest) {
        try {
            AccountType existingAccountType =
                    accountTypeRepo.findAccountTypeByAccountTypeName(associatedOrganizationRequest.getAccountTypeName());
            if(existingAccountType==null){
                return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.NO_CONTENT, "Provided account type doesnt exist"), HttpStatus.NO_CONTENT);
            }

            AccountAssociatedOrganization accountAssociatedOrganization =
                    new AccountAssociatedOrganization();
            ;
            if( accountAssociatedOrganizationRepo.findAccountAssociatedOrganizationByOrganizationNameAndActive(associatedOrganizationRequest.getAccountAssociatedOrganizationName(),'Y').isEmpty()){
                return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.ALREADY_EXISTS, "Provided organization name doesnt already"), HttpStatus.CONFLICT);

            }
            accountAssociatedOrganization.setOrganizationName(associatedOrganizationRequest.getAccountAssociatedOrganizationName());
            accountAssociatedOrganization.setAccountType(existingAccountType);
            accountAssociatedOrganization.setActive('Y');
            existingAccountType.getAccountAssociatedOrganizations().add(accountAssociatedOrganization);
            accountAssociatedOrganizationRepo.save(accountAssociatedOrganization);
            accountTypeRepo.save(existingAccountType);
            return new ResponseEntity<>(ResponseUtility.successResponseWithMessage(ResponseConstants.OK, "Account associated organization added"), HttpStatus.OK);
        } catch (Exception e) {
            log.error("AccountAssociatedOrganizationService :: addAccountAssociatedOrganization",e);
            return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.INTERNAL_ERROR, "Some exception occurred"), HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @Override
    public ResponseEntity<Object> getAccountAssociatedOrganizationByAccountType(String accountTypeName) {
        try {
            List<String> listOfOrganizationName =
                    accountAssociatedOrganizationRepo.findListOfOrganizationNameByAccountTypeName(accountTypeName);
            if(listOfOrganizationName.isEmpty()){
                return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.NO_CONTENT, "No organization name under account type:"+accountTypeName), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(ResponseUtility.successResponseWithMessageAndBody(ResponseConstants.OK,
                    "Organization names by account type: "+accountTypeName, listOfOrganizationName), HttpStatus.OK);
        } catch (Exception e) {
            log.error("AccountAssociatedOrganizationService :: addAccountAssociatedOrganization",e);
            return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.INTERNAL_ERROR, "Some exception occurred"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
