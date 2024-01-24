package com.librarymanagement.LibraryApplication.services.serviceImpls;

import com.librarymanagement.LibraryApplication.entities.AccountAssociatedOrganization;
import com.librarymanagement.LibraryApplication.entities.AccountType;
import com.librarymanagement.LibraryApplication.entities.LibrarianAccount;
import com.librarymanagement.LibraryApplication.entities.User;
import com.librarymanagement.LibraryApplication.mappers.LibrarianAccountMapper;
import com.librarymanagement.LibraryApplication.models.requests.LibrarianAccountRequest;
import com.librarymanagement.LibraryApplication.repositories.AccountAssociatedOrganizationRepo;
import com.librarymanagement.LibraryApplication.repositories.AccountTypeRepo;
import com.librarymanagement.LibraryApplication.repositories.LibrarianAccountRepo;
import com.librarymanagement.LibraryApplication.repositories.UserRepo;
import com.librarymanagement.LibraryApplication.services.LibrarianAccountService;
import com.librarymanagement.LibraryApplication.utils.ResponseConstants;
import com.librarymanagement.LibraryApplication.utils.ResponseUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

@Log4j2
@Service
@RequiredArgsConstructor
public class LibrarianAccountServiceImpl implements LibrarianAccountService {
    private final UserRepo userRepo;
    private final AccountTypeRepo accountTypeRepo;
    private final LibrarianAccountRepo librarianAccountRepo;
    private final AccountAssociatedOrganizationRepo accountAssociatedOrganizationRepo;

    @Override
    public ResponseEntity<Object> addLibrarianAccount(List<LibrarianAccountRequest> librarianAccountRequest) {

        AtomicBoolean verifiedFlag = new AtomicBoolean(true);
        List<LibrarianAccount> librarianAccounts = new ArrayList<>();
        List<String> accTypeList = accountTypeRepo.findAccountTypeNameList();
        Set<String> accTypeSet = new HashSet<>();
        librarianAccountRequest.forEach(librarianAccountRequest1 ->  {
            if(accTypeSet.contains(librarianAccountRequest1.getAccountTypeName()) || !accTypeList.contains(librarianAccountRequest1.getAccountTypeName()) ){
                verifiedFlag.set(false);
            }
            accTypeSet.add(librarianAccountRequest1.getAccountTypeName());
        });
        if(!verifiedFlag.get()){
            return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.BAD_REQUEST,"Duplicate Account Type for single user"), HttpStatus.BAD_REQUEST);
        }
         SecurityContext context = SecurityContextHolder.getContext();
         Principal principal = context.getAuthentication();
         String username = principal.getName();
        User user = userRepo.findUserByUsername(username);

        for(LibrarianAccountRequest request : librarianAccountRequest){
            LibrarianAccount librarianAccount;
        List<String> associatedOrganizationNameList =
                accountAssociatedOrganizationRepo.findListOfOrganizationNameByAccountTypeName(request.getAccountTypeName());
            AccountType accountType =
                    accountTypeRepo.findAccountTypeByAccountTypeName(request.getAccountTypeName());
            Optional<AccountAssociatedOrganization> accountAssociatedOrganization =
                    accountAssociatedOrganizationRepo.findAccountAssociatedOrganizationByOrganizationNameAndActive(request.getAccountAssociatedOrganizationName(), 'Y');
        if(!associatedOrganizationNameList.contains(request.getAccountAssociatedOrganizationName())){
            return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.BAD_REQUEST,"Invalid account associated organization name"), HttpStatus.BAD_REQUEST);
        }
        librarianAccount = LibrarianAccountMapper.mapToLibrarianAccount(request);
        librarianAccount.setAccountType(accountType);
        librarianAccount.setAccountAssociatedOrganization(accountAssociatedOrganization.get());
        librarianAccount.setLibrarian(user);
        librarianAccounts.add(librarianAccount);
        }
        librarianAccountRepo.saveAll(librarianAccounts);
        return new ResponseEntity<>(ResponseUtility.successResponseWithMessage(ResponseConstants.OK, "Librarian account details saved successfully"), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<Object> getDetails() {
        SecurityContext context = SecurityContextHolder.getContext();
        Principal principal = context.getAuthentication();
        String username = principal.getName();
        User user = userRepo.findUserByUsername(username);
        List<LibrarianAccount> librarianAccountDetails = librarianAccountRepo.getAdminAccountDetailsByUsername(username);
        if(librarianAccountDetails.isEmpty()){
            return  new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.NO_CONTENT, "No account details added"), HttpStatus.NOT_FOUND);
        }
        return  new ResponseEntity<>(ResponseUtility.failureResponseWithMessageAndBody(ResponseConstants.OK, "Account details found", LibrarianAccountMapper.mapToLibrarianAccountDto(librarianAccountDetails)), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<Object> modifyDetails(List<LibrarianAccountRequest> librarianAccountRequest) {
        AtomicBoolean verifiedFlag = new AtomicBoolean(true);
        List<String> accTypeList = accountTypeRepo.findAccountTypeNameList();
        Set<String> accTypeSet = new HashSet<>();
        librarianAccountRequest.forEach(librarianAccountRequest1 ->  {
            if(accTypeSet.contains(librarianAccountRequest1.getAccountTypeName()) || !accTypeList.contains(librarianAccountRequest1.getAccountTypeName()) ){
                verifiedFlag.set(false);
            }
            accTypeSet.add(librarianAccountRequest1.getAccountTypeName());
        });
        if(!verifiedFlag.get()){
            return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.BAD_REQUEST,"Duplicate Account Type for single user"), HttpStatus.BAD_REQUEST);
        }
        SecurityContext context = SecurityContextHolder.getContext();
        Principal principal = context.getAuthentication();
        String username = principal.getName();
        User user = userRepo.findUserByUsername(username);
        List<LibrarianAccount> librarianAccounts =
                librarianAccountRepo.getAdminAccountDetailsByUsername(username);
        if(librarianAccounts.size() > librarianAccountRequest.size()){
            List<String> accTypesPresent = librarianAccounts.stream().map(librarianAccount -> {
                return librarianAccount.getAccountType().getAccountTypeName();
            }).toList();
            List<String> accTypesInReq =
                    librarianAccountRequest.stream().map(LibrarianAccountRequest::getAccountTypeName).toList();
            List<String> accTypesToRemove =
                    accTypesPresent.stream().filter(accType -> !accTypesInReq.contains(accType)).toList();
            accTypesToRemove.forEach(accType->{
                this.librarianAccountRepo.makeAccountTypeInactiveByUser(accType,user);
            });
        }

        for(LibrarianAccountRequest request : librarianAccountRequest){

            List<String> associatedOrganizationNameList =
                    accountAssociatedOrganizationRepo.findListOfOrganizationNameByAccountTypeName(request.getAccountTypeName());
            AccountType accountType =
                    accountTypeRepo.findAccountTypeByAccountTypeName(request.getAccountTypeName());
            Optional<AccountAssociatedOrganization> accountAssociatedOrganization =
                    accountAssociatedOrganizationRepo.findAccountAssociatedOrganizationByOrganizationNameAndActive(request.getAccountAssociatedOrganizationName(), 'Y');
            if(!associatedOrganizationNameList.contains(request.getAccountAssociatedOrganizationName())){
                return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.BAD_REQUEST,"Invalid account associated organization name"), HttpStatus.BAD_REQUEST);
            }
            LibrarianAccount librarianAccount =
                    this.librarianAccountRepo.getLibrarianAccountByAccountTypeAndLibrarian(accountType, user);
            if(librarianAccount ==null){
                librarianAccount = LibrarianAccountMapper.mapToLibrarianAccount(request);
                librarianAccount.setAccountType(accountType);
                librarianAccount.setAccountAssociatedOrganization(accountAssociatedOrganization.get());
                librarianAccount.setLibrarian(user);
                librarianAccount.setModifiedDate(LocalDate.now());
            }else {
                librarianAccount.setAccountNumber(request.getAccountNumber());
                librarianAccount.setAccountName(request.getAccountName());
                librarianAccount.setModifiedDate(LocalDate.now());
                librarianAccount.setAccountAssociatedOrganization(accountAssociatedOrganization.get());
                librarianAccountRepo.save(librarianAccount);
            }
            librarianAccountRepo.save(librarianAccount);

        }
        return new ResponseEntity<>(ResponseUtility.successResponseWithMessage(ResponseConstants.OK, "Librarian account details updated successfully"), HttpStatus.OK);
    }


    @Override
    public ResponseEntity<Object> makeAccDetailsInactive() {
        SecurityContext context = SecurityContextHolder.getContext();
        Principal principal = context.getAuthentication();
        String username = principal.getName();
        User user = userRepo.findUserByUsername(username);
        int rows=  librarianAccountRepo.makeAccountDetailInactive(user);
        if(rows<1){
            return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.INTERNAL_ERROR, "Failed to clear account details"), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResponseUtility.successResponseWithMessage(ResponseConstants.OK, "Cleared details"), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<Object> getClearFineAccountByAccountType(String accountTypeName){
        LibrarianAccount account = this.librarianAccountRepo.getClearFineAccountByAccountType(accountTypeName);
        if (account==null){
            return new ResponseEntity<>(ResponseUtility.successResponseWithMessage(ResponseConstants.NO_CONTENT, "Details not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ResponseUtility.successResponseWithMessageAndBody(ResponseConstants.OK, "Details retrieved", LibrarianAccountMapper.mapToLibrarianAccountDto(account)), HttpStatus.OK);

    }

}
