package com.librarymanagement.LibraryApplication.services.serviceImpls;

import com.librarymanagement.LibraryApplication.entities.User;
import com.librarymanagement.LibraryApplication.mappers.UserMapper;
import com.librarymanagement.LibraryApplication.models.dtos.ManageUserDto;
import com.librarymanagement.LibraryApplication.models.dtos.UserDto;
import com.librarymanagement.LibraryApplication.models.requests.ChangePasswordRequest;
import com.librarymanagement.LibraryApplication.models.requests.ChangeUserDetailsReq;
import com.librarymanagement.LibraryApplication.models.requests.LockUserRequest;
import com.librarymanagement.LibraryApplication.models.requests.UserRegisterRequest;
import com.librarymanagement.LibraryApplication.repositories.UserRepo;
import com.librarymanagement.LibraryApplication.services.UserService;
import com.librarymanagement.LibraryApplication.utils.ResponseConstants;
import com.librarymanagement.LibraryApplication.utils.ResponseUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service("UserServiceImpl")
@RequiredArgsConstructor
@Primary
public class UserServiceImpl implements UserService , UserDetailsService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;


    @Override
    public ResponseEntity<Object> addNewUser(UserRegisterRequest userRegisterRequest) {
        try {
            User user = userRepo.findUserByUsername(userRegisterRequest.getUsername());
            if (user != null) {
                return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.ALREADY_EXISTS,
                        "Username should be unique"), HttpStatus.CONFLICT);
            }
            String encodedPassword = passwordEncoder.encode(userRegisterRequest.getPassword());
            user = UserMapper.mapToUser(userRegisterRequest, encodedPassword);
            user.setReserveAndBorrowList(new ArrayList<>());
            userRepo.save(user);
            return new ResponseEntity<>(ResponseUtility.successResponseWithMessage(ResponseConstants.CREATED,
                    "User registered successfully"), HttpStatus.OK);
        } catch (Exception e) {
            log.error("UserServiceImpl :: registerUser", e);
            return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.INTERNAL_ERROR,
                    "Unable to register user"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Object> setUpAdmin(UserRegisterRequest userRegisterRequest) {
        try {
            User user = userRepo.findUserByUsername(userRegisterRequest.getUsername());
            if (user != null) {
                return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.ALREADY_EXISTS,
                        "Username should be unique"), HttpStatus.CONFLICT);
            }
            String encodedPassword = passwordEncoder.encode(userRegisterRequest.getPassword());
            user = UserMapper.mapToAdmin(userRegisterRequest, encodedPassword);
            user.setReserveAndBorrowList(new ArrayList<>());
            userRepo.save(user);
            return new ResponseEntity<>(ResponseUtility.successResponseWithMessage(ResponseConstants.CREATED,
                    "User registered successfully"), HttpStatus.OK);
        } catch (Exception e) {
            log.error("UserServiceImpl :: registerUser", e);
            return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.INTERNAL_ERROR,
                    "Unable to register user"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Object> retrieveUser(String username) {
        try {
            User user = userRepo.findUserByUsername(username);
            if (user == null) {
                return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.NOT_FOUND,
                        "User doesnt exist"), HttpStatus.NO_CONTENT);
            }
            UserDto userDto = UserMapper.mapUserToBaseUserDto(user);
            return new ResponseEntity<>(ResponseUtility.successResponseWithMessageAndBody(ResponseConstants.OK,
                    "User information retrieved", userDto), HttpStatus.OK);
        } catch (Exception e) {
            log.error("UserServiceImpl :: retrieveUser", e);
            return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.INTERNAL_ERROR,
                    "Unable to retrieve user information"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Object> failedLoginAttempt(String username) {
        User user = userRepo.findUserByUsername(username);
        user.setPasswordAttemptCount(user.getPasswordAttemptCount() + 1);
        if (user.getPasswordAttemptCount() > 4) {
            user.setIsNotLocked(Boolean.FALSE);
            user.setRemark("More than 5 unsuccessful login attempt");
            userRepo.save(user);
            return new ResponseEntity<>(ResponseUtility.authenticationFailureWithMessage(ResponseConstants.BAD_CREDENTIALS,
                    "5 wrong attempts occurred. Account is locked"),
                    HttpStatus.UNAUTHORIZED);
        }
        userRepo.save(user);
        return new ResponseEntity<>(ResponseUtility.authenticationFailureWithMessage(ResponseConstants.BAD_CREDENTIALS,
                "Invalid username or password. Attempts left:" + (5 - user.getPasswordAttemptCount())),
                HttpStatus.UNAUTHORIZED);

    }

    @Override
    public ResponseEntity<Object> unlockUserAccount(String username) {
        try {
            User user = userRepo.findUserByUsername(username);
            if (user == null) {
                return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.NO_CONTENT, "User doesnt exist"), HttpStatus.NO_CONTENT);
            }
            if (user.getIsNotLocked()) {
                return new ResponseEntity<>(ResponseUtility.successResponseWithMessage(ResponseConstants.OK, "User already unlocked"), HttpStatus.OK);
            }
            userRepo.unlockUser(username);
            return new ResponseEntity<>(ResponseUtility.authenticationSuccessWithMessage(ResponseConstants.OK, "User unlocked"),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(ResponseUtility.authenticationSuccessWithMessage(ResponseConstants.OK, "Failed to unlock user"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public ResponseEntity<Object> lockUserAccount(LockUserRequest lockUserRequest) {
        try {
            User user = userRepo.findUserByUsername(lockUserRequest.getUsername());
            if (user == null) {
                return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.NO_CONTENT, "User doesnt exist"), HttpStatus.NO_CONTENT);
            }
            if (!user.getIsNotLocked() && user.getRemark() == null) {
                return new ResponseEntity<>(ResponseUtility.successResponseWithMessage(ResponseConstants.OK, "Account already locked"), HttpStatus.OK);
            }
            if (!user.getIsNotLocked()) {
                return new ResponseEntity<>(ResponseUtility.successResponseWithMessage(ResponseConstants.OK, "Account already locked. " + " Remark: " + user.getRemark()), HttpStatus.OK);
            }
            userRepo.lockUserByUsername(lockUserRequest.getUsername(), lockUserRequest.getRemark());
            return new ResponseEntity<>(ResponseUtility.authenticationSuccessWithMessage(ResponseConstants.OK, "Account locked"),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(ResponseUtility.authenticationSuccessWithMessage(ResponseConstants.INTERNAL_ERROR, "Failed to lock user"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Object> changePassword(ChangePasswordRequest changePasswordRequest) {

        if (!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getReEnterNewPassword())) {
            return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.NOT_ALLOWED, "New password and Re-entered passwords don't match"), HttpStatus.BAD_REQUEST);
        }
        User user = userRepo.findUserByUsername(changePasswordRequest.getUsername());
        String currentPassword = user.getPassword();
        String newPassword = passwordEncoder.encode(changePasswordRequest.getNewPassword());
        if (!passwordEncoder.matches(changePasswordRequest.getCurrentPassword(), currentPassword)) {
            return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.NOT_ALLOWED, "Invalid current password"), HttpStatus.BAD_REQUEST);
        }
        if (passwordEncoder.matches(changePasswordRequest.getNewPassword(), currentPassword)) {
            return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.NOT_ALLOWED, "New password cannot be same as the old password"), HttpStatus.BAD_REQUEST);

        }
        user.setPassword(newPassword);
        userRepo.save(user);
        return new ResponseEntity<>(ResponseUtility.successResponseWithMessage(ResponseConstants.OK, "Password changed"), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<Object> userNameExists(String username) {
        User user = this.userRepo.findUserByUsername(username);
        if (user == null) {
            return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.NO_CONTENT,
                    "Username" +
                    " doesn't" +
                    " exist"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ResponseUtility.successResponseWithMessage(ResponseConstants.OK, "Username is taken"), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> searchUserByUsername(String username) {
        String text = username.trim().toLowerCase();
        List<User> users = this.userRepo.findUsersByUsernameLike("%"+text+"%");
        if (users.isEmpty()) {
            return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.NO_CONTENT,
                    "No matches found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ResponseUtility.successResponseWithMessageAndBody(ResponseConstants.OK, "User(s) retrieved", UserMapper.mapToManageUserDto(users)), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> getAll() {
        SecurityContext context = SecurityContextHolder.getContext();
        Principal principal = context.getAuthentication();
        String username = principal.getName();
        List<User> users = userRepo.findAllUsersToManage(username);
        if(users.isEmpty()){
            return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.NO_CONTENT,
                    "No Users"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ResponseUtility.successResponseWithMessageAndBody(ResponseConstants.OK, "User(s) retrieved", UserMapper.mapToManageUserDto(users)), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<Object> getLockedUsers() {
        List<User> lockedUsers = this.userRepo.findLockedUsers();
        if(lockedUsers.isEmpty()){
            return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.NO_CONTENT,
                    "No Locked Users"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ResponseUtility.successResponseWithMessageAndBody(ResponseConstants.OK, "Locked User(s) retrieved", UserMapper.mapToManageUserDto(lockedUsers)), HttpStatus.OK);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findUserByUsername(username);
    }

    @Override
    public ResponseEntity<Object> getUserDetailByUsername(String username) {
        try {
            User user = userRepo.findUserByUsername(username);
            if (user == null) {
                return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.NOT_FOUND,
                        "User doesnt exist"), HttpStatus.NO_CONTENT);
            }
            ManageUserDto userDto = UserMapper.mapToManageUserDto(user);
            return new ResponseEntity<>(ResponseUtility.successResponseWithMessageAndBody(ResponseConstants.OK,
                    "User information retrieved", userDto), HttpStatus.OK);
        } catch (Exception e) {
            log.error("UserServiceImpl :: retrieveUser", e);
            return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.INTERNAL_ERROR,
                    "Unable to retrieve user information"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Object> updateUserDetails(ChangeUserDetailsReq changeUserDetailsReq) {
        try {
            SecurityContext context = SecurityContextHolder.getContext();
            Principal principal  = context.getAuthentication();
            String username = principal.getName();
            User user = userRepo.findUserByUsername(username);
            user.setFirstName(changeUserDetailsReq.getFirstName());
            user.setLastName(changeUserDetailsReq.getLastName());
            user.setEmail(changeUserDetailsReq.getEmail());
            user.setPhone(changeUserDetailsReq.getPhone());
            user.setAddress(changeUserDetailsReq.getAddress());
            userRepo.save(user);
            return new ResponseEntity<>(ResponseUtility.successResponseWithMessage(ResponseConstants.OK, "Detais updated successfully"), HttpStatus.OK);
        } catch (Exception e) {
            log.error("UserServiceImpl :: updateUserDetails",e);
            return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.INTERNAL_ERROR,
                    "Unable to update user information"), HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }


}
