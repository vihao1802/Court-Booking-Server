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
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;
import com.court_booking_project.court_booking_server.service.implementations.ReservationServiceImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;


@Slf4j
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
    public Page<ReservationResponse> getMyReservations(Pageable pageable) {
        return reservationService.getMyReservations(pageable);
    }
    @GetMapping("/latest")
    public ResponseEntity<List<ReservationResponse>> getLatestReservations(@RequestParam int limit) {
        return  new ResponseEntity<>(reservationService.getLatestReservation(limit), HttpStatus.OK);
    }

    @GetMapping("/paginated")
    public Page<ReservationResponse> getReservations(
            @RequestParam(name="search",required = false) String search,
            @RequestParam(name="from",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fromDate,
            @RequestParam(name="to",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate toDate,
            Pageable pageable
    ) {

        return reservationService.findFilteredReservations(search, fromDate, toDate, pageable);
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
        return zalPayService.createPaymentZaloPay(id);
    }

    @PostMapping("/{id}/zalo-pay/callback")
    public String handleZaloCallback( @PathVariable String id,@RequestBody ZaloPayCallBackDTO jsonString) throws Exception {
        return zalPayService.handleZaloCallback(id,jsonString);  // Correct: Instance call
    }

    @GetMapping("/{id}/getInvoice")
    public ResponseEntity<byte[]> getInvoice(@PathVariable String id) {
        // Tạo hóa đơn PDF
        byte[] pdfBytes = reservationService.generateInvoice(id);

        // Trả về PDF
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=invoice.pdf");

        return ResponseEntity.status(HttpStatus.OK)
                .headers(headers)
                .body(pdfBytes);
    }
}
