package com.court_booking_project.court_booking_server.dto.request.zalopay;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class ZaloPayCallBackDTO {
    String data;
    String mac;
    int type;
}
