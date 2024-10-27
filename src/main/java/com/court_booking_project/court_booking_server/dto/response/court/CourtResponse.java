package com.court_booking_project.court_booking_server.dto.response.court;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CourtResponse {
    String id;
    String courtName;
    String courtDescription;
    String courtAddress;
    long rentalPricePerHour;
    int minimumRentalTime;
    int maximumRentalTime;
    String courtTypeId;
}