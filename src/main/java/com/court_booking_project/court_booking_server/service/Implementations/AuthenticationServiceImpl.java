package com.court_booking_project.court_booking_server.service.Implementations;

import com.court_booking_project.court_booking_server.Exception.AppException;
import com.court_booking_project.court_booking_server.Exception.ErrorCode;
import com.court_booking_project.court_booking_server.dto.Request.IntrospectRequest;
import com.court_booking_project.court_booking_server.dto.Request.LoginRequestDto;
import com.court_booking_project.court_booking_server.dto.Response.AuthenticationResponse;
import com.court_booking_project.court_booking_server.dto.Response.IntrospectResponse;
import com.court_booking_project.court_booking_server.service.Interfaces.IAuthenticationService;
import com.court_booking_project.court_booking_server.service.Interfaces.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.*;
import com.nimbusds.jwt.*;

@Service
@Slf4j
public class AuthenticationServiceImpl implements IAuthenticationService {
    private final IUserService userService;

    @Value("${jwt.secretSigningKey}")
    String SECRET_SIGNING_KEY;

    @Value("${jwt.expirationTime}")
    long EXPIRATION_TIME;

    public AuthenticationServiceImpl( IUserService userService) {
        this.userService = userService;
    }
    public AuthenticationResponse login(LoginRequestDto loginRequestDto) {
        var user = userService.findByEmail(loginRequestDto.getEmail());
        if(user == null)
            throw new AppException(ErrorCode.USER_NOT_EXISTED);

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean verifyPassword = passwordEncoder.matches(loginRequestDto.getPassword(),user.getPassword());

        if(!verifyPassword)
            throw new AppException(ErrorCode.INVALID_PASSWORD);

        var token = generateToken(loginRequestDto.getEmail());

        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }

    @Override
    public IntrospectResponse introspect(IntrospectRequest introspectRequest) {

        var token = introspectRequest.getToken();

        var verified = verifyToken(token);

        return IntrospectResponse.builder()
                .valid(verified)
                .build();
    }


    public String generateToken(String email) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(email)
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(EXPIRATION_TIME, ChronoUnit.SECONDS).toEpochMilli()))
                .claim("email", email)
                .build();

        Payload payload= new Payload(claimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(SECRET_SIGNING_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Error signing token", e);
            throw new RuntimeException(e);
        }
    }
    public boolean verifyToken(String token) {
        if(token.equals("")) throw new AppException(ErrorCode.INVALID_TOKEN);

        try {
            JWSVerifier verifier = new MACVerifier(SECRET_SIGNING_KEY.getBytes());

            SignedJWT signedJWT = SignedJWT.parse(token);

            var expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();

            var verified = signedJWT.verify(verifier);

            return verified && expirationTime.after(new Date());

        } catch (JOSEException | ParseException e) {
            log.error("Error verifying token", e);
            throw new RuntimeException(e);
        }
    }
}

