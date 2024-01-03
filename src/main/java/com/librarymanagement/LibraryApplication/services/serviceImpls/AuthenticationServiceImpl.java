package com.librarymanagement.LibraryApplication.services.serviceImpls;

import com.librarymanagement.LibraryApplication.entities.User;
import com.librarymanagement.LibraryApplication.enums.Role;
import com.librarymanagement.LibraryApplication.jwtconfigs.JwtService;
import com.librarymanagement.LibraryApplication.mappers.UserMapper;
import com.librarymanagement.LibraryApplication.models.requests.AuthenticationRequest;
import com.librarymanagement.LibraryApplication.models.requests.UserRegisterRequest;
import com.librarymanagement.LibraryApplication.repositories.UserRepo;
import com.librarymanagement.LibraryApplication.services.AuthenticationService;
import com.librarymanagement.LibraryApplication.services.UserService;
import com.librarymanagement.LibraryApplication.utils.ResponseConstants;
import com.librarymanagement.LibraryApplication.utils.ResponseUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepo userRepo;
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<Object> register(UserRegisterRequest userRegisterRequest) {
        User user = userRepo.findUserByUsername(userRegisterRequest.getUsername());
        if (user != null) {
            return new ResponseEntity<>(ResponseUtility.failureResponseWithMessage(ResponseConstants.ALREADY_EXISTS,
                    "Username already taken"), HttpStatus.CONFLICT);
        }
        String encodedPassword = passwordEncoder.encode(userRegisterRequest.getPassword());
         user = UserMapper.mapToUser(userRegisterRequest, encodedPassword);
        user.setRole(Role.USER);
        userRepo.save(user);
//        var jwtToken = jwtService.generateToken(user);
        return new ResponseEntity<>(ResponseUtility.successResponseWithMessage(ResponseConstants.OK,
                "User registered successfully"), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> authenticate(AuthenticationRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                            authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            log.error("AuthenticationService :: authenticate", e);
            return userService.failedLoginAttempt(authenticationRequest.getUsername());
        } catch (AccessDeniedException e) {
            log.error("AuthenticationService :: authenticate", e);
            return new ResponseEntity<>(ResponseUtility.authenticationFailureWithMessage(ResponseConstants.FORBIDDEN,
                    "User doesnt have required authority"), HttpStatus.UNAUTHORIZED);
        } catch (DisabledException e) {
            log.error("AuthenticationService :: authenticate", e);
            return new ResponseEntity<>(ResponseUtility.authenticationFailureWithMessage(ResponseConstants.DISABLED_USER,
                    "User is disabled"), HttpStatus.UNAUTHORIZED);
        } catch (UsernameNotFoundException | InternalAuthenticationServiceException e) {
            log.error("AuthenticationService :: authenticate", e);
            return new ResponseEntity<>(ResponseUtility.authenticationFailureWithMessage(ResponseConstants.BAD_CREDENTIALS,
                    "Username or password is incorrect "), HttpStatus.UNAUTHORIZED);
        } catch (LockedException e) {
            log.error(" AuthenticationService :: authenticate", e);
            return new ResponseEntity<>(ResponseUtility.authenticationFailureWithMessage(ResponseConstants.LOCKED_USER, "User account locked due to more than 5 unsuccessful attempts"), HttpStatus.UNAUTHORIZED);
        }
        var user = userRepo.findUserByUsername(authenticationRequest.getUsername());
        Integer updateCount = userRepo.refreshLoginAttempts(user.getUsername());
        var jwtToken = jwtService.generateToken(user);
        return new ResponseEntity<>(ResponseUtility.successResponseWithMessageAndBody(ResponseConstants.OK,
                "User authenticated successfully", jwtToken), HttpStatus.OK);
    }
}
