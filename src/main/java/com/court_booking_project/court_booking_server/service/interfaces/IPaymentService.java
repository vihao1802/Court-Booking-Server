package com.court_booking_project.court_booking_server.service.interfaces;

import com.court_booking_project.court_booking_server.entity.PaymentMethod;

import java.util.List;

public interface IPaymentService {
    PaymentMethod get(String paymentId);
    List<PaymentMethod> getAll();
    void add(PaymentMethod paymentMethod);
}
