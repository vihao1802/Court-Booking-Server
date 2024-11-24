package com.court_booking_project.court_booking_server.dto.request.user;

import com.court_booking_project.court_booking_server.custom_annotation.PasswordConstraint;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdatePasswordRequest {
    @NotNull
    String oldPassword;

    @PasswordConstraint(message = "INVALID_PASSWORD")
    String newPassword;
}
