package com.librarymanagement.LibraryApplication.services.serviceImpls;

import com.librarymanagement.LibraryApplication.entities.User;
import com.librarymanagement.LibraryApplication.mappers.UserMapper;
import com.librarymanagement.LibraryApplication.models.dtos.userdtos.UserDto;
import com.librarymanagement.LibraryApplication.models.requests.UserRegisterRequest;
import com.librarymanagement.LibraryApplication.repositories.UserRepo;
import com.librarymanagement.LibraryApplication.services.UserService;
import com.librarymanagement.LibraryApplication.utils.ResponseConstants;
import com.librarymanagement.LibraryApplication.utils.ResponseUtility;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Log4j2
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
private UserRepo userRepo;
    @Override
    public ResponseEntity<Object> registerUser(UserRegisterRequest userRegisterRequest) {
        try {
            User user = userRepo.findUserByUsername(userRegisterRequest.getUsername());
            if(user != null){
                return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.ALREADY_EXISTS,
                        "Username should be unique"), HttpStatus.OK );
            }
            user = UserMapper.mapToUser(userRegisterRequest);
            user.setStatus(Boolean.TRUE);
            user.setReserveAndBorrowList(new ArrayList<>());
            userRepo.save(user);
            return new ResponseEntity<>(ResponseUtility.successResponseWithMessage(ResponseConstants.CREATED,
                    "User registered successfully"), HttpStatus.OK);
        } catch (Exception e) {
            log.error("UserServiceImpl :: registerUser", e);
            return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.INTERNAL_ERROR,
                    "Unable to register user"), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<Object> retrieveUser(String username) {
        try {
            User user = userRepo.findUserByUsername(username);
            if(user==null){
                return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.NOT_FOUND,
                        "User doesnt exist"), HttpStatus.OK);
            }
            UserDto userDto = UserMapper.mapUserToBaseUserDto(user);
            return new ResponseEntity<>(ResponseUtility.successResponseWithMessageAndBody(ResponseConstants.OK,
                    "User information retrieved", userDto), HttpStatus.OK);
        } catch (Exception e) {
            log.error("UserServiceImpl :: retrieveUser", e);
            return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.INTERNAL_ERROR,
                    "Unable to retrieve user information"), HttpStatus.OK);
        }
    }
}
