package com.court_booking_project.court_booking_server.service.implementations;

import com.court_booking_project.court_booking_server.config.MomoSettings;
import com.court_booking_project.court_booking_server.entity.Reservation;
import com.court_booking_project.court_booking_server.repository.IReservationRepository;
import com.court_booking_project.court_booking_server.service.interfaces.IReservationService;
import com.court_booking_project.court_booking_server.dto.Request.momo.MomoCreatePaymentDTO;
import com.court_booking_project.court_booking_server.dto.Request.momo.MomoRequestCreatePaymentDTO;
import com.court_booking_project.court_booking_server.dto.Request.momo.MomoCallbackDTO;

import com.court_booking_project.court_booking_server.utils.momo.CreateSignature;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationServiceImpl implements IReservationService {

    private final IReservationRepository reservationRepository;
    MomoService momoService;
    MomoSettings momoSettings;
    CreateSignature createSignature;

    public ReservationServiceImpl(IReservationRepository reservationRepository) {
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

    public MomoCreatePaymentDTO createPaymentMomo (String id, MomoRequestCreatePaymentDTO request) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new RuntimeException("Reservation not found"));
        try {
            return momoService.createPayment(reservation, request);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public void handleMomoCallBack(String id, MomoCallbackDTO callbackDto) {

        // First, generate the signature to compare

        String rawData = String.format("accessKey=%s&amount=%.0f&extraData=%s&message=%s&orderId=%s" +
                        "&orderInfo=%s&orderType=%s&partnerCode=%s&payType=%s&requestId=%s" +
                        "&responseTime=%d&resultCode=%d&transId=%d",
                momoSettings.getAccessKey(),
                callbackDto.getAmount(),
                callbackDto.getExtraData(),
                callbackDto.getMessage(),
                callbackDto.getOrderId(),
                callbackDto.getOrderInfo(),
                callbackDto.getOrderType(),
                callbackDto.getPartnerCode(),
                callbackDto.getPayType(),
                callbackDto.getRequestId(),
                callbackDto.getResponseTime(),
                callbackDto.getResultCode(),
                callbackDto.getTransId());

        // Create the expected signature from the rawData
        String expectedSignature = null;
        try {
            expectedSignature = createSignature.computeHmacSha256(rawData, momoSettings.getSecretKey());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Compare the received signature with the expected signature
        if (callbackDto.getSignature().equals(expectedSignature) && callbackDto.getResultCode() == 0) {
            // Proceed with processing the payment
            Reservation reservation = reservationRepository.findById(id).orElse(null);
            if (reservation != null) {
                reservation.setReservationState(1);
                reservationRepository.save(reservation); // Save the updated bill
            }
        } else {
            // Handle signature mismatch or other issues
            System.out.println("Signature mismatch or unsuccessful result code.");
        }
    }
}
