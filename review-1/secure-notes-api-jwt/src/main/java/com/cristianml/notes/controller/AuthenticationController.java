package com.cristianml.notes.controller;

import com.cristianml.notes.dto.AuthCreateUserRequest;
import com.cristianml.notes.dto.AuthLoginRequest;
import com.cristianml.notes.dto.AuthResponse;
import com.cristianml.notes.security.service.UserDetailsServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private UserDetailsServiceImpl userDetailsService;

    public AuthenticationController(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/log-in")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthLoginRequest userRequest) {
        return new ResponseEntity<>(this.userDetailsService.loginUser(userRequest), HttpStatus.OK);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<AuthResponse> signUp(@RequestBody @Valid AuthCreateUserRequest createUserRequest) {
        return new ResponseEntity<>(this.userDetailsService.createUser(createUserRequest), HttpStatus.CREATED);
    }

}
