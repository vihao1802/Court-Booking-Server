package com.court_booking_project.court_booking_server.controller;

import com.court_booking_project.court_booking_server.dto.request.momo.MomoCallbackDTO;
import com.court_booking_project.court_booking_server.dto.request.momo.MomoCreatePaymentDTO;
import com.court_booking_project.court_booking_server.dto.request.momo.MomoRequestCreatePaymentDTO;
import com.court_booking_project.court_booking_server.dto.request.reservation.CreateReservationRequest;
import com.court_booking_project.court_booking_server.dto.request.reservation.UpdateReservationRequest;
import com.court_booking_project.court_booking_server.dto.request.zalopay.ZaloPayRequestCreatePaymentDTO;
import com.court_booking_project.court_booking_server.dto.request.zalopay.ZaloPayCallBackDTO;
import com.court_booking_project.court_booking_server.dto.response.ApiResponse;
import com.court_booking_project.court_booking_server.dto.response.reservation.ReservationResponse;
import com.court_booking_project.court_booking_server.service.implementations.ZaloPayService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.court_booking_project.court_booking_server.service.implementations.ReservationServiceImpl;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("${spring.application.api-prefix}/reservations")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReservationController {
    ReservationServiceImpl reservationService;
    ZaloPayService zalPayService;

    @GetMapping
    public List<ReservationResponse> getAllCourts() {
        return reservationService.getAll();
    }

    @GetMapping("/{id}")
    public ReservationResponse getCourt(@PathVariable String id) {
        return reservationService.get(id);
    }

    @GetMapping("/my-reservations")
    public ResponseEntity<List<ReservationResponse>> getMyReservations() {
        return  new ResponseEntity<>(reservationService.getMyReservations(), HttpStatus.OK);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationResponse addCourt(@RequestBody @Valid CreateReservationRequest request) {
        return reservationService.add(request);
    }

    @PutMapping("/{id}")
    public ReservationResponse updateCourt(@PathVariable String id,@RequestBody @Valid UpdateReservationRequest request) {
        return reservationService.update(id, request);
    }

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
    public ResponseEntity<?> createPaymentZaloPay( @PathVariable String id) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(zalPayService.createPaymentZaloPay(id));
    }

    @PostMapping("/{id}/zalo-pay/callback")
    public String handleZaloCallback( @PathVariable String id,@RequestBody ZaloPayCallBackDTO jsonString) throws Exception {
        return zalPayService.handleZaloCallback(id,jsonString);  // Correct: Instance call
    }
}
