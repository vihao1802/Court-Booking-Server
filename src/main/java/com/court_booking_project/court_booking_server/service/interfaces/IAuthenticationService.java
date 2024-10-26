package com.court_booking_project.court_booking_server.service.interfaces;


import com.court_booking_project.court_booking_server.dto.request.authentication.IntrospectRequest;
import com.court_booking_project.court_booking_server.dto.request.authentication.LoginRequestDto;
import com.court_booking_project.court_booking_server.dto.response.authentication.AuthenticationResponse;
import com.court_booking_project.court_booking_server.dto.response.authentication.IntrospectResponse;

public interface IAuthenticationService {
    AuthenticationResponse login(LoginRequestDto loginRequestDto);
    IntrospectResponse introspect(IntrospectRequest introspectRequest);
}
