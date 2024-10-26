package com.court_booking_project.court_booking_server.dto.request.court;

import jakarta.validation.constraints.Min;
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
public class UpdateCourtRequest {
    @NotEmpty(message = "courtName cannot be an empty string")
    String courtName;

    @NotEmpty(message = "courtDescription cannot be an empty string")
    String courtDescription;

    @NotEmpty(message = "courtAddress cannot be an empty string")
    String courtAddress;

    @NotNull(message = "rentalPricePerHour is required")
    @Min(value = 1, message = "rentalPricePerHour must be greater than 0")
    long rentalPricePerHour;

    @NotNull(message = "minimumRentalTime is required")
    @Min(value = 1, message = "minimumRentalTime must be greater than 0")
    int minimumRentalTime;

    @NotNull(message = "maximumRentalTime is required")
    @Min(value = 1, message = "maximumRentalTime must be greater than 0")
    int maximumRentalTime;

    @NotEmpty(message = "courtTypeId cannot be an empty string")
    String courtTypeId;
}
