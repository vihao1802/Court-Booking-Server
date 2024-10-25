package com.court_booking_project.court_booking_server.controller;

import com.court_booking_project.court_booking_server.dto.Request.momo.MomoCallbackDTO;
import com.court_booking_project.court_booking_server.dto.Request.momo.MomoCreatePaymentDTO;
import com.court_booking_project.court_booking_server.dto.Request.momo.MomoRequestCreatePaymentDTO;
import com.court_booking_project.court_booking_server.dto.Request.zalopay.ZaloPayRequestCreatePaymentDTO;
import com.court_booking_project.court_booking_server.dto.Request.zalopay.ZaloPayCallBackDTO;
import com.court_booking_project.court_booking_server.service.implementations.ZaloPayService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.court_booking_project.court_booking_server.service.implementations.ReservationServiceImpl;


@RestController
@RequiredArgsConstructor
@RequestMapping("${spring.application.api-prefix}/reservations")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReservationController {
    ReservationServiceImpl reservationService;
    ZaloPayService zalPayService;

    @PostMapping("/{id}/payment/momo")
    public ResponseEntity<MomoCreatePaymentDTO> createPaymentMomo(@PathVariable String id, @RequestBody MomoRequestCreatePaymentDTO request) {
        MomoCreatePaymentDTO payment = reservationService.createPaymentMomo(id, request);
        return ResponseEntity.ok(payment);
    }

    @PostMapping("/{id}/momo-callback")
    public ResponseEntity<Void> handleMomoCallBack(@PathVariable String id, @RequestBody MomoCallbackDTO callbackDto) {
        reservationService.handleMomoCallBack(id, callbackDto);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/{id}/payment/zalo-pay")
    public ResponseEntity<?> createPaymentZaloPay( @PathVariable String id,@RequestBody ZaloPayRequestCreatePaymentDTO request) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(zalPayService.createPaymentZaloPay(id,request));
    }

    @PostMapping("/zalo-pay/callback")
    public String handleZaloCallback(@RequestBody ZaloPayCallBackDTO jsonString) throws Exception {
        return zalPayService.handleZaloCallback(jsonString);  // Correct: Instance call
    }
}
