package com.court_booking_project.court_booking_server.service.interfaces;

import com.court_booking_project.court_booking_server.dto.request.momo.MomoCallbackDTO;
import com.court_booking_project.court_booking_server.dto.request.momo.MomoCreatePaymentDTO;
import com.court_booking_project.court_booking_server.dto.request.momo.MomoRequestCreatePaymentDTO;
import com.court_booking_project.court_booking_server.dto.request.reservation.CreateReservationRequest;
import com.court_booking_project.court_booking_server.dto.request.reservation.UpdateReservationRequest;
import com.court_booking_project.court_booking_server.dto.response.reservation.ReservationResponse;
import com.court_booking_project.court_booking_server.dto.response.statistic.RevenueByMonthResponse;

import java.util.Date;
import java.util.List;

public interface IReservationService {
    ReservationResponse get(String id);
    List<ReservationResponse> getAll();
    List<ReservationResponse> getMyReservations();
    ReservationResponse add(CreateReservationRequest request);
    ReservationResponse update(String id,UpdateReservationRequest request);
    MomoCreatePaymentDTO createPaymentMomo (String id, MomoRequestCreatePaymentDTO request);
    void handleMomoCallBack(String id, MomoCallbackDTO callbackDto);
    Integer getTotalBookingHours(Date startDate, Date endDate);
    Integer getTotalProfit(Date startDate, Date endDate);
    List<RevenueByMonthResponse> getRevenueByMonths(Date startDate, Date endDate);
}
