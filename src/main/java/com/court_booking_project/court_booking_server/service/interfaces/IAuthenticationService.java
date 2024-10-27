package com.court_booking_project.court_booking_server.service.interfaces;


import com.court_booking_project.court_booking_server.dto.request.authentication.IntrospectRequest;
import com.court_booking_project.court_booking_server.dto.request.authentication.LoginRequestDto;
import com.court_booking_project.court_booking_server.dto.request.authentication.LogoutRequest;
import com.court_booking_project.court_booking_server.dto.request.authentication.RefreshTokenRequest;
import com.court_booking_project.court_booking_server.dto.response.authentication.AuthenticationResponse;
import com.court_booking_project.court_booking_server.dto.response.authentication.IntrospectResponse;
import com.court_booking_project.court_booking_server.exception.AppException;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;


public interface IAuthenticationService {
    AuthenticationResponse login(LoginRequestDto loginRequestDto);
    IntrospectResponse introspect(IntrospectRequest introspectRequest) throws JOSEException, ParseException;
    void logout(LogoutRequest logoutRequest);
    AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest)throws AppException, ParseException, JOSEException;
}
