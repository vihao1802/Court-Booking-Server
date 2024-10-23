package com.court_booking_project.court_booking_server.service;

import com.court_booking_project.court_booking_server.entity.Reservation;
import com.court_booking_project.court_booking_server.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService{

    private final ReservationRepository reservationRepository;

    public ReservationServiceImpl(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public Reservation get(String id) {
        return reservationRepository.findById(id).get();
    }

    @Override
    public List<Reservation> getAll() {
        return reservationRepository.findAll();
    }

    @Override
    public void add(Reservation reservation) {
        reservationRepository.save(reservation);
    }
}
