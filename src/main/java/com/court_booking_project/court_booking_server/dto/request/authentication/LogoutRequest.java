package com.court_booking_project.court_booking_server.dto.request.authentication;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LogoutRequest {
    @NotNull(message = "INVALID_TOKEN")
    @NotEmpty(message = "INVALID_TOKEN")
    String token;
}
