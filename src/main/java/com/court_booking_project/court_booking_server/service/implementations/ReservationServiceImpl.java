package com.court_booking_project.court_booking_server.service.implementations;

import com.court_booking_project.court_booking_server.config.MomoSettings;
import com.court_booking_project.court_booking_server.constant.ReservationState;
import com.court_booking_project.court_booking_server.dto.request.reservation.CreateReservationRequest;
import com.court_booking_project.court_booking_server.dto.request.reservation.UpdateReservationRequest;
import com.court_booking_project.court_booking_server.dto.response.reservation.ReservationResponse;
import com.court_booking_project.court_booking_server.dto.response.statistic.RevenueByMonthResponse;
import com.court_booking_project.court_booking_server.entity.Reservation;
import com.court_booking_project.court_booking_server.exception.AppException;
import com.court_booking_project.court_booking_server.exception.ErrorCode;
import com.court_booking_project.court_booking_server.mapper.ReservationMapper;
import com.court_booking_project.court_booking_server.repository.ICourtRepository;
import com.court_booking_project.court_booking_server.repository.IReservationRepository;
import com.court_booking_project.court_booking_server.repository.IUserRepository;
import com.court_booking_project.court_booking_server.service.interfaces.IReservationService;
import com.court_booking_project.court_booking_server.dto.request.momo.MomoCreatePaymentDTO;
import com.court_booking_project.court_booking_server.dto.request.momo.MomoRequestCreatePaymentDTO;
import com.court_booking_project.court_booking_server.dto.request.momo.MomoCallbackDTO;

import com.court_booking_project.court_booking_server.utils.momo.CreateSignature;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class ReservationServiceImpl implements IReservationService {

    IReservationRepository reservationRepository;
    ICourtRepository courtRepository;
    IUserRepository userRepository;
    MomoService momoService;
    MomoSettings momoSettings;
    CreateSignature createSignature;
    ReservationMapper reservationMapper;

    @Override
    public ReservationResponse get(String id) {
        return reservationRepository.findById(id).map(reservationMapper::convertEntityToResponse).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_RESERVATION_ID));
    }

    @Override
    public List<ReservationResponse> getAll() {
        return reservationRepository.findAll().stream().map(reservationMapper::convertEntityToResponse).toList();
    }

    @Override
    public List<ReservationResponse> getMyReservations() {
        var context = SecurityContextHolder.getContext();

        String email = context.getAuthentication().getName();

        var user = userRepository.findByEmail(email);

        if  (user.isEmpty())
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        return reservationRepository.findByUserOrderByCreatedAt(user.get()).stream().map(reservationMapper::convertEntityToResponse).toList();
    }

    @Override
    public Page<ReservationResponse> findFilteredReservations(String search, Date fromDate, Date toDate, Pageable pageable) {

        Page<Reservation> reservationPage =  reservationRepository.findFilteredReservations(search, fromDate, toDate, pageable);

        return reservationPage.map(reservationMapper::convertEntityToResponse);
    }

    @Override
    public ReservationResponse add(CreateReservationRequest request) {
        var court = courtRepository.findById(request.getCourtId());
        var user = userRepository.findById(request.getUserId());

        if(court.isEmpty())
            throw new AppException(ErrorCode.NOT_FOUND_COURT_ID);
        if(user.isEmpty())
            throw new AppException(ErrorCode.NOT_FOUND_USER_ID);

        Reservation reservation = reservationMapper.convertCreateDTOtoEntity(request);

        reservation.setCourt(court.get());
        reservation.setUser(user.get());

        reservationRepository.save(reservation);

        return reservationMapper.convertEntityToResponse(reservation);
    }

    @Override
    public ReservationResponse update(String id, UpdateReservationRequest request) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_RESERVATION_ID));
        try {
            reservationMapper.convertUpdateDTOtoEntity(reservation, request);
            reservationRepository.save(reservation);
            return reservationMapper.convertEntityToResponse(reservation);
        } catch (Exception e) {
            throw new AppException(ErrorCode.NOT_FOUND_PAYMENT_METHOD);
        }
    }

    public MomoCreatePaymentDTO createPaymentMomo (String id, MomoRequestCreatePaymentDTO request) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_RESERVATION_ID));
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
                reservation.setReservationState(ReservationState.fromCode(1));
                reservationRepository.save(reservation); // Save the updated bill
            }
        } else {
            // Handle signature mismatch or other issues
            System.out.println("Signature mismatch or unsuccessful result code.");
        }
    }

    public Integer getTotalBookingHours(Date startDate, Date endDate) {
        return reservationRepository.getTotalBookingHours(startDate, endDate);
    }
    public Integer getTotalProfit(Date startDate, Date endDate) {
        return reservationRepository.getTotalProfit(startDate, endDate);
    }

    @Override
    public List<RevenueByMonthResponse> getRevenueByMonths(Date startDate, Date endDate) {
//        return reservationRepository.getRevenueByMonths(startDate, endDate);
        List<Object[]> results = reservationRepository.getRevenueByMonths(startDate, endDate);
        return results.stream()
                .map(row -> RevenueByMonthResponse.builder()
                        .month((Integer) row[0])
                        .year((Integer) row[1])
                        .revenue((BigDecimal) row[2])
                        .bookingHours((BigDecimal) row[3])
                        .build())
                .collect(Collectors.toList());
    }

}
