package com.court_booking_project.court_booking_server.dto.request.court_type;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateCourtTypeRequest {
    @NotEmpty(message = "courtTypeName cannot be an empty string")
    String courtTypeName;

    @NotNull(message = "isDisabled cannot be null")
    int isDisabled;
}
