package com.cristian.exercise.controller;

import com.cristian.exercise.dto.AuthLoginRequest;
import com.cristian.exercise.dto.AuthResponse;
import com.cristian.exercise.security.service.UserDetailsServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserDetailsServiceImpl userDetailsService;

    @PostMapping("/log-in")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthLoginRequest request){
        return new ResponseEntity<>(userDetailsService.loginUser(request), HttpStatus.OK);
    }

}
