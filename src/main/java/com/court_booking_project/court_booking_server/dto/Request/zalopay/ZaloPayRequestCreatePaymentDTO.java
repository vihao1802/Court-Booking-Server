package com.court_booking_project.court_booking_server.dto.Request.zalopay;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class ZaloPayRequestCreatePaymentDTO {
    Integer totalPrice;
    String userName;
}
