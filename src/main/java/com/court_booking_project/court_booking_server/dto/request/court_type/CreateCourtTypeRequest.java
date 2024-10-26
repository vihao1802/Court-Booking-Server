package com.court_booking_project.court_booking_server.dto.request.court_type;

import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateCourtTypeRequest {
    @NotEmpty(message = "courtTypeName cannot be an empty string")
    String courtTypeName;
}
