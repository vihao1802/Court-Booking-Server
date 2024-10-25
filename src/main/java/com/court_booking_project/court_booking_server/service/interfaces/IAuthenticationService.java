package com.court_booking_project.court_booking_server.service.interfaces;


import com.court_booking_project.court_booking_server.dto.request.IntrospectRequest;
import com.court_booking_project.court_booking_server.dto.request.LoginRequestDto;
import com.court_booking_project.court_booking_server.dto.response.AuthenticationResponse;
import com.court_booking_project.court_booking_server.dto.response.IntrospectResponse;

public interface IAuthenticationService {
    AuthenticationResponse login(LoginRequestDto loginRequestDto);
    IntrospectResponse introspect(IntrospectRequest introspectRequest);
}
