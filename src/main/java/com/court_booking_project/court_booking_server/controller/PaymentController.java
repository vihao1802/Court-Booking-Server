package com.court_booking_project.court_booking_server.controller;

import com.court_booking_project.court_booking_server.dto.ZaloPayCallBackDTO;
import com.court_booking_project.court_booking_server.dto.ZaloPayRequestCreatePaymentDTO;
import com.court_booking_project.court_booking_server.service.ZaloPayService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("${spring.application.api-prefix}/payment")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentController {
    ZaloPayService zalPayService;

    @PostMapping("/{id}/zalo-pay")
    public ResponseEntity<?> createPaymentZaloPay( @PathVariable String id,@RequestBody ZaloPayRequestCreatePaymentDTO request) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(zalPayService.createPaymentZaloPay(id,request));
    }

    @PostMapping("/zalo-pay/callback")
    public String handleZaloCallback(@RequestBody ZaloPayCallBackDTO jsonString) throws Exception {
        return zalPayService.handleZaloCallback(jsonString);  // Correct: Instance call
    }
}
