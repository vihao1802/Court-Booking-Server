package com.court_booking_project.court_booking_server.dto.temp_request.momo;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ExtraData {
        String reservationId;

        public ExtraData(String reservationId) {
                this.reservationId = reservationId;
        }
}
