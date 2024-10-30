package com.court_booking_project.court_booking_server.service.implementations;

import com.court_booking_project.court_booking_server.entity.PaymentMethod;
import com.court_booking_project.court_booking_server.repository.IPaymentRepository;
import com.court_booking_project.court_booking_server.service.interfaces.IPaymentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentServiceImpl implements IPaymentService {
    private final IPaymentRepository paymentRepository;

    public PaymentServiceImpl(IPaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public PaymentMethod get(String paymentId) {
        return paymentRepository.findById(paymentId).get();
    }

    @Override
    public List<PaymentMethod> getAll() {
        return paymentRepository.findAll();
    }

    @Override
    public void add(PaymentMethod paymentMethod) {
        paymentRepository.save(paymentMethod);
    }
}
