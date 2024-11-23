package com.court_booking_project.court_booking_server.dto.request.user;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DisableUserRequest {
    @Min(value = 0, message = "Value must be 0 or 1")
    @Max(value = 1, message = "Value must be 0 or 1")
    int isDisabled;
}