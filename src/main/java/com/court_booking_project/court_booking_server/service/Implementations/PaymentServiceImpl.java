package com.court_booking_project.court_booking_server.service.Implementations;

import com.court_booking_project.court_booking_server.entity.Payment;
import com.court_booking_project.court_booking_server.repository.IPaymentRepository;
import com.court_booking_project.court_booking_server.service.Interfaces.IPaymentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentServiceImpl implements IPaymentService {
    private final IPaymentRepository paymentRepository;

    public PaymentServiceImpl(IPaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Payment get(String paymentId) {
        return paymentRepository.findById(paymentId).get();
    }

    @Override
    public List<Payment> getAll() {
        return paymentRepository.findAll();
    }

    @Override
    public void add(Payment payment) {
        paymentRepository.save(payment);
    }
}
