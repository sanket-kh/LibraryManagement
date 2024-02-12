package com.librarymanagement.LibraryApplication.services.serviceImpls;

import com.librarymanagement.LibraryApplication.entities.AccountAssociatedOrganization;
import com.librarymanagement.LibraryApplication.entities.AccountType;
import com.librarymanagement.LibraryApplication.entities.LibrarianAccount;
import com.librarymanagement.LibraryApplication.entities.User;
import com.librarymanagement.LibraryApplication.mappers.LibrarianAccountMapper;
import com.librarymanagement.LibraryApplication.models.dtos.LibrarianAccountDto;
import com.librarymanagement.LibraryApplication.models.requests.LibrarianAccountRequest;
import com.librarymanagement.LibraryApplication.repositories.AccountAssociatedOrganizationRepo;
import com.librarymanagement.LibraryApplication.repositories.AccountTypeRepo;
import com.librarymanagement.LibraryApplication.repositories.LibrarianAccountRepo;
import com.librarymanagement.LibraryApplication.repositories.UserRepo;
import com.librarymanagement.LibraryApplication.services.LibrarianAccountService;
import com.librarymanagement.LibraryApplication.utils.ResponseConstants;
import com.librarymanagement.LibraryApplication.utils.ResponseUtility;
import com.librarymanagement.LibraryApplication.utils.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

@Log4j2
@Service
@RequiredArgsConstructor
public class LibrarianAccountServiceImpl implements LibrarianAccountService {
    private final UserRepo userRepo;
    private final AccountTypeRepo accountTypeRepo;
    private final LibrarianAccountRepo librarianAccountRepo;
    private final AccountAssociatedOrganizationRepo accountAssociatedOrganizationRepo;

    private ResponseEntity<Object> checkLibrarianAccountReqValidity(List<LibrarianAccountRequest> librarianAccountRequest) {
        AtomicBoolean verifiedFlag = new AtomicBoolean(true);
        List<String> accTypeList = accountTypeRepo.findAccountTypeNameList();
        Set<String> accTypeSet = new HashSet<>();
        librarianAccountRequest.forEach(librarianAccountRequest1 -> {
            if (accTypeSet.contains(librarianAccountRequest1.getAccountTypeName()) ||
                !accTypeList.contains(librarianAccountRequest1.getAccountTypeName())) {
                verifiedFlag.set(false);
            }
            accTypeSet.add(librarianAccountRequest1.getAccountTypeName());
        });
        if (!verifiedFlag.get()) {
            return ResponseUtility.failureResponseWithMessage(ResponseConstants.BAD_REQUEST,
                    "Duplicate Account Type for single user", HttpStatus.BAD_REQUEST);
        }
        return null;
    }

    @Override
    public ResponseEntity<Object> addLibrarianAccount(List<LibrarianAccountRequest> librarianAccountRequest) {

        try {
            List<LibrarianAccount> librarianAccounts = new ArrayList<>();
            if (checkLibrarianAccountReqValidity(librarianAccountRequest) != null) {
                return checkLibrarianAccountReqValidity(librarianAccountRequest);
            }
            String username = Utils.getUsernameFromContext();
            User user = userRepo.findUserByUsername(username);

            for (LibrarianAccountRequest request : librarianAccountRequest) {
                LibrarianAccount librarianAccount;
                if(checkAccountAssociatedOrganizationValidity(request) == null) {
                    return ResponseUtility.failureResponseWithMessage(ResponseConstants.BAD_REQUEST,
                            "Invalid account associated organization name", HttpStatus.BAD_REQUEST);
                }
                 Result result = checkAccountAssociatedOrganizationValidity(request);
                assert result != null;
                librarianAccount = LibrarianAccountMapper.mapToAddLibrarianAccount(request,
                        result.accountType, result.accountAssociatedOrganization, user);
                librarianAccounts.add(librarianAccount);
            }
            librarianAccountRepo.saveAll(librarianAccounts);
            return ResponseUtility.successResponseWithMessage(ResponseConstants.OK,
                    "Librarian account details saved successfully", HttpStatus.OK);
        } catch (Exception e) {
            log.error("LibrarianAccountService :: addLibrarianAccount", e);
            return ResponseUtility.failureResponseWithMessage(ResponseConstants.INTERNAL_ERROR,
                    "Some error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    private Result checkAccountAssociatedOrganizationValidity(LibrarianAccountRequest request){
        List<String> associatedOrganizationNameList =
                accountAssociatedOrganizationRepo.findListOfOrganizationNameByAccountTypeName(request.getAccountTypeName());
        Result result = new Result(accountTypeRepo.findAccountTypeByAccountTypeName(request.getAccountTypeName()),
                accountAssociatedOrganizationRepo.findAccountAssociatedOrganizationByOrganizationNameAndActive(request.getAccountAssociatedOrganizationName(), 'Y'));
        if (!associatedOrganizationNameList.contains(request.getAccountAssociatedOrganizationName())) {
            return null;
        }else return result;
    }


    @Override
    public ResponseEntity<Object> getDetails() {
        try {
            String username = Utils.getUsernameFromContext();
            List<LibrarianAccount> librarianAccountDetails = librarianAccountRepo.getAdminAccountDetailsByUsername(username);
            if (librarianAccountDetails.isEmpty()) {
                return ResponseUtility.failureResponseWithMessage(ResponseConstants.NO_CONTENT,
                        "No account details added", HttpStatus.NOT_FOUND);
            }
            List<LibrarianAccountDto> librarianAccountDtos =
                    LibrarianAccountMapper.mapToLibrarianAccountDto(librarianAccountDetails);
            return ResponseUtility.failureResponseWithMessageAndBody(ResponseConstants.OK,
                    "Account details found", librarianAccountDtos, HttpStatus.OK);
        } catch (Exception e) {
            log.error("LibrarianAccountService :: getDetails", e);
            return ResponseUtility.failureResponseWithMessage(ResponseConstants.INTERNAL_ERROR,
                    "Some error occurred", HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    @Override
    public ResponseEntity<Object> modifyDetails(List<LibrarianAccountRequest> librarianAccountRequest) {
        try {
            if (checkLibrarianAccountReqValidity(librarianAccountRequest) != null) {
                return checkLibrarianAccountReqValidity(librarianAccountRequest);
            }
            String username = Utils.getUsernameFromContext();
            User user = userRepo.findUserByUsername(username);
            List<LibrarianAccount> librarianAccounts =
                    librarianAccountRepo.getAdminAccountDetailsByUsername(username);
            if (librarianAccounts.size() > librarianAccountRequest.size()) {
                List<String> accTypesPresent = librarianAccounts.stream().map(librarianAccount ->
                        librarianAccount.getAccountType().getAccountTypeName()).toList();
                List<String> accTypesInReq =
                        librarianAccountRequest.stream().map(LibrarianAccountRequest::getAccountTypeName).toList();
                List<String> accTypesToRemove =
                        accTypesPresent.stream().filter(accType -> !accTypesInReq.contains(accType)).toList();
                accTypesToRemove.forEach(accType ->
                        this.librarianAccountRepo.makeAccountTypeInactiveByUser(accType, user));
            }

            for (LibrarianAccountRequest request : librarianAccountRequest) {

                if(checkAccountAssociatedOrganizationValidity(request) == null) {
                    return ResponseUtility.failureResponseWithMessage(ResponseConstants.BAD_REQUEST,
                            "Invalid account associated organization name", HttpStatus.BAD_REQUEST);
                }
                Result result = checkAccountAssociatedOrganizationValidity(request);
                assert result != null;
                LibrarianAccount librarianAccount =
                        this.librarianAccountRepo.getLibrarianAccountByAccountTypeAndLibrarian(result.accountType, user);
                if (librarianAccount == null) {
                    librarianAccount = LibrarianAccountMapper.mapToAddLibrarianAccount(request,
                            result.accountType, result.accountAssociatedOrganization, user);
                } else {
                    LibrarianAccountMapper.mapToModifyLibrarianAccount(request,
                            result.accountType,
                            result.accountAssociatedOrganization,
                            user, librarianAccount);
                    librarianAccountRepo.save(librarianAccount);
                }
                librarianAccountRepo.save(librarianAccount);

            }
            return ResponseUtility.successResponseWithMessage(ResponseConstants.OK,
                    "Librarian account details updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            log.error("LibrarianAccountService :: getDetails", e);
            return ResponseUtility.failureResponseWithMessage(ResponseConstants.INTERNAL_ERROR,
                    "Some error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Object> makeAccDetailsInactive() {
        try {
            SecurityContext context = SecurityContextHolder.getContext();
            Principal principal = context.getAuthentication();
            String username = principal.getName();
            User user = userRepo.findUserByUsername(username);
            int rows = librarianAccountRepo.makeAccountDetailInactive(user);
            if (rows < 1) {
                return ResponseUtility.failureResponseWithMessage(ResponseConstants.INTERNAL_ERROR,
                        "Failed to clear account details", HttpStatus.OK);
            }
            return ResponseUtility.successResponseWithMessage(ResponseConstants.OK,
                    "Cleared details", HttpStatus.OK);
        } catch (Exception e) {
            log.error("LibrarianAccountService :: makeAccDetailsInactive", e);
            return ResponseUtility.failureResponseWithMessage(ResponseConstants.INTERNAL_ERROR,
                    "Some error occurred", HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    @Override
    public ResponseEntity<Object> getClearFineAccountByAccountType(String accountTypeName) {
        try {
            LibrarianAccount account =
                    this.librarianAccountRepo.getClearFineAccountByAccountTypeAndUsername(accountTypeName);
            if (account == null) {
                return ResponseUtility.successResponseWithMessage(ResponseConstants.NO_CONTENT,
                        "Details not found", HttpStatus.NOT_FOUND);
            }
            LibrarianAccountDto librarianAccountDto = LibrarianAccountMapper.mapToLibrarianAccountDto(account);
            return ResponseUtility.successResponseWithMessageAndBody(ResponseConstants.OK,
                    "Details retrieved", librarianAccountDto, HttpStatus.OK);
        } catch (Exception e) {
            log.error("LibrarianAccountService :: getClearFineAccountByAccountType", e);
            return ResponseUtility.failureResponseWithMessage(ResponseConstants.INTERNAL_ERROR,
                    "Some error occurred", HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    private record Result(AccountType accountType,
                          AccountAssociatedOrganization accountAssociatedOrganization) {

    }

}
