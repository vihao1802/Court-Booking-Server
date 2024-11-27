package com.court_booking_project.court_booking_server.controller;

import com.court_booking_project.court_booking_server.dto.request.authentication.IntrospectRequest;
import com.court_booking_project.court_booking_server.dto.request.authentication.LoginRequestDto;
import com.court_booking_project.court_booking_server.dto.request.authentication.LogoutRequest;
import com.court_booking_project.court_booking_server.dto.request.authentication.RefreshTokenRequest;
import com.court_booking_project.court_booking_server.dto.response.authentication.AuthenticationResponse;
import com.court_booking_project.court_booking_server.dto.response.authentication.IntrospectResponse;
import com.court_booking_project.court_booking_server.dto.response.authentication.OTPResponse;
import com.court_booking_project.court_booking_server.dto.response.authentication.UserResponse;
import com.court_booking_project.court_booking_server.service.interfaces.IAuthenticationService;
import com.nimbusds.jose.JOSEException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@Slf4j
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
    public ResponseEntity<IntrospectResponse> introspect(@RequestBody IntrospectRequest introspectRequest) throws ParseException, JOSEException {
        return new ResponseEntity<>(authenticationService.introspect(introspectRequest), HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody @Valid LogoutRequest logoutRequest) {
        authenticationService.logout(logoutRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthenticationResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) throws ParseException, JOSEException {
        return new ResponseEntity<>(authenticationService.refreshToken(refreshTokenRequest), HttpStatus.OK);
    }

}
