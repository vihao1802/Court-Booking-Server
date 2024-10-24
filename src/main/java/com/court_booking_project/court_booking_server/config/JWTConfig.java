package com.court_booking_project.court_booking_server.config;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class JWTConfig {
    @Value("${jwt.secretSigningKey}")
    String SECRET;

    @Value("${jwt.expirationTime}")
    long EXPIRATION_TIME;}

