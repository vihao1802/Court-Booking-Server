package com.court_booking_project.court_booking_server.service.Interfaces;


import com.court_booking_project.court_booking_server.dto.Request.IntrospectRequest;
import com.court_booking_project.court_booking_server.dto.Request.LoginRequestDto;
import com.court_booking_project.court_booking_server.dto.Response.AuthenticationResponse;
import com.court_booking_project.court_booking_server.dto.Response.IntrospectResponse;

public interface IAuthenticationService {
    AuthenticationResponse login(LoginRequestDto loginRequestDto);
    IntrospectResponse introspect(IntrospectRequest introspectRequest);
}
