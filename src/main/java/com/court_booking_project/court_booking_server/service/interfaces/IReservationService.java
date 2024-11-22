package com.court_booking_project.court_booking_server.service.interfaces;

import com.court_booking_project.court_booking_server.dto.request.momo.MomoCallbackDTO;
import com.court_booking_project.court_booking_server.dto.request.momo.MomoCreatePaymentDTO;
import com.court_booking_project.court_booking_server.dto.request.momo.MomoRequestCreatePaymentDTO;
import com.court_booking_project.court_booking_server.dto.request.reservation.CreateReservationRequest;
import com.court_booking_project.court_booking_server.dto.request.reservation.UpdateReservationRequest;
import com.court_booking_project.court_booking_server.dto.response.reservation.ReservationResponse;
import com.court_booking_project.court_booking_server.entity.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface IReservationService {
    ReservationResponse get(String id);
    List<ReservationResponse> getAll();
    List<ReservationResponse> getMyReservations();
    Page<ReservationResponse> findFilteredReservations(String search, Date fromDate, Date toDate, Pageable pageable);
    ReservationResponse add(CreateReservationRequest request);
    ReservationResponse update(String id,UpdateReservationRequest request);
    MomoCreatePaymentDTO createPaymentMomo (String id, MomoRequestCreatePaymentDTO request);
    void handleMomoCallBack(String id, MomoCallbackDTO callbackDto);
}
