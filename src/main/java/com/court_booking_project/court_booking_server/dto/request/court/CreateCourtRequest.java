package com.court_booking_project.court_booking_server.dto.request.court;

import com.court_booking_project.court_booking_server.dto.request.court_image.CreateCourtImageRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateCourtRequest {
    @NotEmpty(message = "EMPTY_COURT_NAME")
    String courtName;

    @NotEmpty(message = "EMPTY_COURT_DESCRIPTION")
    String courtDescription;

    @NotEmpty(message = "EMPTY_ADDRESS")
    String courtAddress;

    @NotNull(message = "REQUIRED_RENTAL_PRICE_PER_HOUR")
    @Min(value = 1, message = "INVALID_RENTAL_PRICE_PER_HOUR")
    long rentalPricePerHour;

    @NotNull(message = "REQUIRED_MINIMUM_RENTAL_TIME")
    @Min(value = 1, message = "INVALID_MINIMUM_RENTAL_TIME")
    int minimumRentalTime;

    @NotNull(message = "REQUIRED_MAXIMUM_RENTAL_TIME")
    @Min(value = 1, message = "INVALID_MAXIMUM_RENTAL_TIME")
    int maximumRentalTime;

    @NotNull(message = "INVALID_COURT_TYPE_ID")
    @NotEmpty(message = "EMPTY_COURT_TYPE_ID")
    String courtTypeId;
}
