package com.court_booking_project.court_booking_server.dto.request.authentication;

import com.court_booking_project.court_booking_server.custom_annotation.PasswordConstraint;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateUserRequest {

    @NotNull(message="INVALID_USERNAME")
    @NotEmpty(message="INVALID_USERNAME")
    private String userName;

    @NotNull(message="INVALID_EMAIL")
    @NotEmpty(message="INVALID_EMAIL")
    @Email(message="INVALID_EMAIL")
    private String email;

    @PasswordConstraint(message = "INVALID_PASSWORD")
    private String password;

    @NotNull(message="INVALID_PHONE_NUMBER")
    @NotEmpty(message="INVALID_PHONE_NUMBER")
    @Size(min=10, max=11, message="INVALID_PHONE_NUMBER")
    private String phoneNumber;

    @NotNull(message="INVALID_DOB")
    @Past(message="INVALID_DOB")
    private LocalDate dayOfBirth;
}
