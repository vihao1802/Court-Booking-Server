package com.court_booking_project.court_booking_server.dto.request.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateUserRequest {
    @NotNull(message="INVALID_USERNAME")
    @NotEmpty(message="INVALID_USERNAME")
    String username;

    @NotNull(message="INVALID_PHONE_NUMBER")
    @NotEmpty(message="INVALID_PHONE_NUMBER")
    @Size(min=10, max=11, message="INVALID_PHONE_NUMBER")
    String phoneNumber;

    @NotNull(message="INVALID_DOB")
    @Past(message="INVALID_DOB")
    LocalDate dayOfBirth;

    @NotNull(message="INVALID_LOCATION")
    String location;
}
