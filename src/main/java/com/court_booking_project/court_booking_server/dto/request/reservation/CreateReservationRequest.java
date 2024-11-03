package com.court_booking_project.court_booking_server.dto.request.reservation;

import com.court_booking_project.court_booking_server.constant.ReservationState;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateReservationRequest {

    @NotEmpty(message = "EMPTY_CHECK_IN_TIME")
    String checkInTime;

    @NotEmpty(message = "EMPTY_CHECK_OUT_TIME")
    String checkOutTime;

    @NotNull(message = "INVALID_TOTAL_PRICE")
    long totalPrice;

    @NotEmpty(message = "INVALID_RESERVATION_DATE")
    String reservationDate;

    @NotEmpty(message = "EMPTY_USER_ID")
    String userId;

    @NotEmpty(message = "EMPTY_COURT_ID")
    String courtId;
}
