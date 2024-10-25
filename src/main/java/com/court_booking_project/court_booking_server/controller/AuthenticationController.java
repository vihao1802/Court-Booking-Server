package com.court_booking_project.court_booking_server.controller;

import com.court_booking_project.court_booking_server.dto.request.authentication.IntrospectRequest;
import com.court_booking_project.court_booking_server.dto.request.authentication.LoginRequestDto;
import com.court_booking_project.court_booking_server.dto.response.authentication.AuthenticationResponse;
import com.court_booking_project.court_booking_server.dto.response.authentication.IntrospectResponse;
import com.court_booking_project.court_booking_server.service.interfaces.IAuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    private final IAuthenticationService authenticationService;

    public AuthenticationController(IAuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequestDto loginRequestDto) {
        return new ResponseEntity<>(authenticationService.login(loginRequestDto), HttpStatus.OK);
    }

    @PostMapping("/introspect")
    public ResponseEntity<IntrospectResponse> introspect(@RequestBody @Valid IntrospectRequest introspectRequest) {
        return new ResponseEntity<>(authenticationService.introspect(introspectRequest), HttpStatus.OK);
    }
}
