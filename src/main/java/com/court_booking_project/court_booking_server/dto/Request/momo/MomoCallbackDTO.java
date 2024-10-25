package com.court_booking_project.court_booking_server.dto.Request.momo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class MomoCallbackDTO {
    String orderType;

    BigDecimal amount;  // Use BigDecimal for currency values

    String partnerCode;

    String orderId;

    private String extraData;

    String signature;

    long transId;  // This can remain as a primitive type

    long responseTime;  // This can remain as a primitive type

    int resultCode;  // This can remain as a primitive type

    String message;

    String payType;

    String requestId;

    private String orderInfo;
}
