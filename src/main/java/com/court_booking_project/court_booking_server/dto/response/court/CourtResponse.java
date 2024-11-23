package com.court_booking_project.court_booking_server.dto.response.court;

import com.court_booking_project.court_booking_server.dto.response.courtImage.CourtImageResponse;
import com.court_booking_project.court_booking_server.dto.response.courtType.CourtTypeResponse;
import com.court_booking_project.court_booking_server.entity.CourtType;
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
    CourtTypeResponse courtType;
    List<CourtImageResponse> courtImageList;
}
