package com.court_booking_project.court_booking_server.service;

import com.court_booking_project.court_booking_server.entity.Reservation;

import java.util.List;

public interface ReservationService {
    Reservation get(String id);
    List<Reservation> getAll();
    void add(Reservation reservation);
}
