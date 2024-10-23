package com.court_booking_project.court_booking_server.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import jakarta.validation.constraints.NotNull;

@Data // Lombok generates getters, setters, equals(), hashCode(), toString() methods for all fields.
@NoArgsConstructor // Lombok generates a no-argument constructor for the class.
@AllArgsConstructor // Lombok generates a constructor with all fields as parameters.
@Builder // Lombok implements the Builder pattern to allow easy object construction.
@FieldDefaults(level = lombok.AccessLevel.PRIVATE) // Ensures that all fields are private unless explicitly set otherwise.
@Component // Registers the class as a Spring component (bean), allowing it to be managed by Spring's IoC container.
@ConfigurationProperties(prefix = "zalopay") // Binds properties starting with the prefix "zalopay" from application.properties
public class ZaloPayConfig {
    @NotNull(message = "app_id can not be null")
    String app_id;

    @NotNull(message = "key1 can not be null")
    String key1;

    @NotNull(message = "key2 can not be null")
    String key2;

    @NotNull(message = "endpoint can not be null")
    String endpoint;

    @NotNull(message = "redirectUrl can not be null")
    String redirectUrl;

    @NotNull(message = "callbackUrl can not be null")
    String callbackUrl;
}
