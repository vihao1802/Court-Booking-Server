package com.court_booking_project.court_booking_server.dto.temp_request.momo;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class MomoCreatePaymentDTO {
        @NotNull(message = "Request ID cannot be null")
        private String requestId;

        private int errorCode; // This can remain as a primitive type since it can be 0

        @NotNull(message = "Order ID cannot be null")
        private String orderId;

        @NotNull(message = "Message cannot be null")
        private String message;

        @NotNull(message = "Pay URL cannot be null")
        private String payUrl;
}
