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
public class UpdateReservationRequest {

    @NotEmpty(message = "INVALID_RESERVATION_STATE")
    Integer reservationState;

    @NotEmpty(message = "")
    String paymentMethodId;
}
