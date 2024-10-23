package com.court_booking_project.court_booking_server.service;

import com.court_booking_project.court_booking_server.entity.Payment;

import java.util.List;

public interface PaymentService {
    Payment get(String paymentId);
    List<Payment> getAll();
    void add(Payment payment);
}
