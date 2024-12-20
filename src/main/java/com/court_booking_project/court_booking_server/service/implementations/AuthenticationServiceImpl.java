package com.court_booking_project.court_booking_server.service.implementations;

import com.court_booking_project.court_booking_server.dto.request.authentication.LogoutRequest;
import com.court_booking_project.court_booking_server.dto.request.authentication.RefreshTokenRequest;
import com.court_booking_project.court_booking_server.dto.response.authentication.OTPResponse;
import com.court_booking_project.court_booking_server.entity.InvalidatedToken;
import com.court_booking_project.court_booking_server.entity.User;
import com.court_booking_project.court_booking_server.exception.AppException;
import com.court_booking_project.court_booking_server.exception.ErrorCode;
import com.court_booking_project.court_booking_server.dto.request.authentication.IntrospectRequest;
import com.court_booking_project.court_booking_server.dto.request.authentication.LoginRequestDto;
import com.court_booking_project.court_booking_server.dto.response.authentication.AuthenticationResponse;
import com.court_booking_project.court_booking_server.dto.response.authentication.IntrospectResponse;
import com.court_booking_project.court_booking_server.repository.IInvalidateTokenRepository;
import com.court_booking_project.court_booking_server.repository.IUserRepository;
import com.court_booking_project.court_booking_server.service.interfaces.IAuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.*;
import com.nimbusds.jwt.*;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationServiceImpl implements IAuthenticationService {
    IUserRepository userRepository;
    IInvalidateTokenRepository invalidateTokenRepository;
    private final MailerService mailerService;

    @NonFinal
    @Value("${jwt.secretSigningKey}")
    String SECRET_SIGNING_KEY;

    @NonFinal
    @Value("${jwt.expirationTime}")
    long EXPIRATION_TIME;

    @NonFinal
    @Value("${jwt.refreshableDuration}")
    long REFRESHABLE_DURATION;

    public AuthenticationResponse login(LoginRequestDto loginRequestDto) {
        var user = userRepository.findByEmail(loginRequestDto.getEmail());
        if(user.isEmpty())
            throw new AppException(ErrorCode.WRONG_AUTHENTICATION_INFO);

        PasswordEncoder passwordEncoder=new BCryptPasswordEncoder(10);
        boolean verifyPassword = passwordEncoder.matches(loginRequestDto.getPassword(),user.get().getPassword());

        if(!verifyPassword)
            throw new AppException(ErrorCode.WRONG_AUTHENTICATION_INFO);

        var token = generateToken(user.get());

        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }

    @Override
    public IntrospectResponse introspect(IntrospectRequest introspectRequest) throws JOSEException, ParseException {
        var token = introspectRequest.getToken();
        boolean isValid = true;

        try{
            verifyToken(token,false);
        }catch (AppException e){
            isValid = false;
        }

        return IntrospectResponse.builder()
                .isValid(isValid)
                .build();
    }

    @Override
    public void logout(LogoutRequest logoutRequest) {
        try {
            var signedJWT = verifyToken(logoutRequest.getToken(),true);

            InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                    .id(signedJWT.getJWTClaimsSet().getJWTID())
                    .expiryDate(signedJWT.getJWTClaimsSet().getExpirationTime())
                    .build();

            invalidateTokenRepository.save(invalidatedToken);

        } catch (AppException | JOSEException | ParseException e) {
            log.error("Token đã hết hạn");
        }
    }

    @Override
    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) throws AppException, ParseException, JOSEException {
        var token = refreshTokenRequest.getToken();

        var signedJWT = verifyToken(token,true);

        Date expiryTime = signedJWT.getJWTClaimsSet().getIssueTime();

        var email = signedJWT.getJWTClaimsSet().getSubject();

        var jtd = signedJWT.getJWTClaimsSet().getJWTID();

        InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                .id(jtd)
                .expiryDate(expiryTime)
                .build();

        invalidateTokenRepository.save(invalidatedToken);

        var user = userRepository.findByEmail(email).orElseThrow(()->new AppException(ErrorCode.UNAUTHENTICATED));
        return AuthenticationResponse.builder()
                .token(generateToken(user))
                .authenticated(true)
                .build();
    }

    @Override
    public String generateToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(user.getEmail())
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(EXPIRATION_TIME, ChronoUnit.SECONDS).toEpochMilli()))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", user.getRole().getRoleName())
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
    private SignedJWT verifyToken(String token,boolean isRefresh) throws AppException,JOSEException, ParseException {
        if (token.isEmpty()) throw new AppException(ErrorCode.UNAUTHENTICATED);

        JWSVerifier verifier = new MACVerifier(SECRET_SIGNING_KEY.getBytes());

            SignedJWT signedJWT = SignedJWT.parse(token);

            var expirationTime =(isRefresh)
                    ? new Date(signedJWT
                        .getJWTClaimsSet()
                        .getIssueTime()
                        .toInstant()
                        .plus(REFRESHABLE_DURATION, ChronoUnit.SECONDS)
                        .toEpochMilli())
                    : signedJWT.getJWTClaimsSet().getExpirationTime();

            var verified = signedJWT.verify(verifier);

            if(!( verified && expirationTime.after(new Date())))
                throw new AppException(ErrorCode.UNAUTHENTICATED);

            if(invalidateTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID()))
                throw new AppException(ErrorCode.UNAUTHENTICATED);

            return signedJWT;

    }
}

