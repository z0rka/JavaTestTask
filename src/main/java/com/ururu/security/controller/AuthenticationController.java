package com.ururu.security.controller;

import com.ururu.security.auth.AuthenticationRequest;
import com.ururu.security.auth.AuthenticationResponse;
import com.ururu.security.service.AuthenticationService;
import com.ururu.security.auth.RegisterRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        log.info("Registration invoked");
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        log.info("Log in invoked");
        return ResponseEntity.ok(service.authenticate(request));
    }
}
