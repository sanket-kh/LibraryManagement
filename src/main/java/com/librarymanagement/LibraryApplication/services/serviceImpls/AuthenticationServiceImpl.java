package com.librarymanagement.LibraryApplication.services.serviceImpls;

import com.librarymanagement.LibraryApplication.entities.User;
import com.librarymanagement.LibraryApplication.enums.Role;
import com.librarymanagement.LibraryApplication.jwtconfigs.JwtService;
import com.librarymanagement.LibraryApplication.mappers.UserMapper;
import com.librarymanagement.LibraryApplication.models.requests.AuthenticationRequest;
import com.librarymanagement.LibraryApplication.models.requests.UserRegisterRequest;
import com.librarymanagement.LibraryApplication.models.responses.AuthResponse;
import com.librarymanagement.LibraryApplication.repositories.UserRepo;
import com.librarymanagement.LibraryApplication.services.AuthenticationService;
import com.librarymanagement.LibraryApplication.services.UserService;
import com.librarymanagement.LibraryApplication.utils.ResponseConstants;
import com.librarymanagement.LibraryApplication.utils.ResponseUtility;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    public AuthenticationServiceImpl(UserRepo userRepo, UserService userServiceImpl, JwtService jwtService, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.userServiceImpl = userServiceImpl;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    private final UserRepo userRepo;
    @Qualifier("UserServiceImpl")
    private final UserService userServiceImpl;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<Object> registerUser(UserRegisterRequest userRegisterRequest) {
        try {
            User user = userRepo.findUserByUsername(userRegisterRequest.getUsername());
            if (user != null) {
                return ResponseUtility.failureResponseWithMessage(ResponseConstants.ALREADY_EXISTS,
                        "Username already taken", HttpStatus.CONFLICT);
            }
            String encodedPassword = passwordEncoder.encode(userRegisterRequest.getPassword());
            user = UserMapper.mapToUser(userRegisterRequest, encodedPassword);
            user.setRole(Role.USER);
            userRepo.save(user);
            return  ResponseUtility.successResponseWithMessage(ResponseConstants.OK,
                    "User registered successfully", HttpStatus.OK);
        } catch (Exception e) {
            log.error("AuthenticationService :: registerUser",e);
            return  ResponseUtility.failureResponseWithMessage(ResponseConstants.INTERNAL_ERROR,
                    "Some exception occurred", HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }
    @Override
    public ResponseEntity<Object> registerLibrarian(UserRegisterRequest userRegisterRequest) {
        User user = userRepo.findUserByUsername(userRegisterRequest.getUsername());
        if (user != null) {
            return  ResponseUtility.failureResponseWithMessage(ResponseConstants.ALREADY_EXISTS,
                    "Username already taken", HttpStatus.CONFLICT);
        }
        String encodedPassword = passwordEncoder.encode(userRegisterRequest.getPassword());
        user = UserMapper.mapToUser(userRegisterRequest, encodedPassword);
        user.setRole(Role.LIBRARIAN);
        userRepo.save(user);
        return  ResponseUtility.successResponseWithMessage(ResponseConstants.OK,
                "Admin registered successfully", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> authenticate(AuthenticationRequest authenticationRequest) {
        var start = System.nanoTime();
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                            authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            log.error("AuthenticationService :: authenticate", e);
            System.out.println(System.nanoTime()-start);
            return userServiceImpl.failedLoginAttempt(authenticationRequest.getUsername());
        } catch (AccessDeniedException e) {
            log.error("AuthenticationService :: authenticate", e);
            return  ResponseUtility.failureResponseWithMessage(ResponseConstants.FORBIDDEN,
                    "User doesnt have required authority", HttpStatus.UNAUTHORIZED);
        } catch (DisabledException e) {
            log.error("AuthenticationService :: authenticate", e);
            return  ResponseUtility.failureResponseWithMessage(ResponseConstants.DISABLED_USER,
                    "User is disabled", HttpStatus.UNAUTHORIZED);
        } catch (UsernameNotFoundException | InternalAuthenticationServiceException e) {
            log.error("AuthenticationService :: authenticate", e);
            return  ResponseUtility.failureResponseWithMessage(ResponseConstants.BAD_CREDENTIALS,
                    "Username or password is incorrect ", HttpStatus.UNAUTHORIZED);
        } catch (LockedException e) {
            log.error(" AuthenticationService :: authenticate", e);
            System.out.println(System.nanoTime()-start);
            return  ResponseUtility.failureResponseWithMessage(ResponseConstants.LOCKED_USER,
                    "User account is locked", HttpStatus.UNAUTHORIZED);
        }
        try {
            User user = userRepo.findUserByUsername(authenticationRequest.getUsername());
            AuthResponse authResponse = new AuthResponse();

            userRepo.refreshLoginAttempts(user.getUsername());
            String jwtToken = jwtService.generateToken(user);
            authResponse.setRole(user.getRole().name());
            authResponse.setAccessToken(jwtToken);
            System.out.println(System.nanoTime()-start);
            return  ResponseUtility.successResponseWithMessageAndBody(ResponseConstants.OK,
                    "User authenticated successfully", authResponse, HttpStatus.OK);
        } catch (Exception e) {
            log.error("AuthenticationService :: authenticate",e);
            return  ResponseUtility.failureResponseWithMessage(ResponseConstants.INTERNAL_ERROR,
                    "Some exception occurred", HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }
}
