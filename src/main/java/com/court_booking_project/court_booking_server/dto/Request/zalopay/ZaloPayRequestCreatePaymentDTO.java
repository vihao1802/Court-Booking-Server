package com.court_booking_project.court_booking_server.dto.Request.zalopay;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class ZaloPayRequestCreatePaymentDTO {

    @NotNull(message = "totalPrice cannot be null")
    @Positive // totalPrice must be greater than 0
    Integer totalPrice;

    @NotEmpty(message = "userName cannot be an empty string")
    String userName;
}
