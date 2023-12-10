package com.librarymanagement.LibraryApplication.services.serviceImpls;

import com.librarymanagement.LibraryApplication.entities.User;
import com.librarymanagement.LibraryApplication.enums.Role;
import com.librarymanagement.LibraryApplication.jwtconfigs.JwtService;
import com.librarymanagement.LibraryApplication.mappers.UserMapper;
import com.librarymanagement.LibraryApplication.models.requests.AuthenticationRequest;
import com.librarymanagement.LibraryApplication.models.requests.UserRegisterRequest;
import com.librarymanagement.LibraryApplication.repositories.UserRepo;
import com.librarymanagement.LibraryApplication.services.AuthenticationService;
import com.librarymanagement.LibraryApplication.utils.ResponseConstants;
import com.librarymanagement.LibraryApplication.utils.ResponseUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepo userRepo;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<Object> register(UserRegisterRequest userRegisterRequest) {
        String encodedPassword = passwordEncoder.encode(userRegisterRequest.getPassword());
        User user  = UserMapper.mapToUser(userRegisterRequest,encodedPassword);
        user.setRole(Role.USER);
        userRepo.save(user);
        var jwtToken = jwtService.generateToken(user);
        return new ResponseEntity<>(ResponseUtility.successResponseWithMessageAndBody(ResponseConstants.OK,
                "User registered successfully", jwtToken), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> authenticate(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                        authenticationRequest.getPassword())
        );
        var user = userRepo.findUserByUsername(authenticationRequest.getUsername());
        var jwtToken = jwtService.generateToken(user);
        return new ResponseEntity<>(ResponseUtility.successResponseWithMessageAndBody(ResponseConstants.OK,
                "User authenticated successfully", jwtToken), HttpStatus.OK);
    }
}
