package com.court_booking_project.court_booking_server.service.interfaces;

import com.court_booking_project.court_booking_server.dto.request.momo.MomoCallbackDTO;
import com.court_booking_project.court_booking_server.dto.request.momo.MomoCreatePaymentDTO;
import com.court_booking_project.court_booking_server.dto.request.momo.MomoRequestCreatePaymentDTO;
import com.court_booking_project.court_booking_server.entity.Reservation;

import java.util.List;

public interface IReservationService {
    Reservation get(String id);
    List<Reservation> getAll();
    void add(Reservation reservation);
    MomoCreatePaymentDTO createPaymentMomo (String id, MomoRequestCreatePaymentDTO request);
    void handleMomoCallBack(String id, MomoCallbackDTO callbackDto);
}
