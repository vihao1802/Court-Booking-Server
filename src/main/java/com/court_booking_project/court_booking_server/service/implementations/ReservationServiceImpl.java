package com.court_booking_project.court_booking_server.service.implementations;

import com.court_booking_project.court_booking_server.config.MomoSettings;
import com.court_booking_project.court_booking_server.constant.ReservationState;
import com.court_booking_project.court_booking_server.dto.request.reservation.CreateReservationRequest;
import com.court_booking_project.court_booking_server.dto.request.reservation.UpdateReservationRequest;
import com.court_booking_project.court_booking_server.dto.response.reservation.ReservationResponse;
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
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Slf4j
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
    public Page<ReservationResponse> findFilteredReservations(String search, LocalDate fromDate, LocalDate toDate, Pageable pageable) {

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

    public byte[] generateInvoice(String id) {
        Reservation reservation = reservationRepository.findById(id).orElse(null);

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            // Tạo PDF writer
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            // Tải font hỗ trợ tiếng Việt
            String fontPath = "src/main/resources/fonts/Roboto-Regular.ttf"; // Đường dẫn tới font
            PdfFont font = PdfFontFactory.createFont(fontPath, PdfEncodings.IDENTITY_H);

            // Thêm tiêu đề hóa đơn
            document.add(new Paragraph("Hóa đơn")
                    .setFont(font)
                    .setBold()
                    .setFontSize(20)
                    .setMarginBottom(20));

            // Thông tin chung
            document.add(new Paragraph("Mã hóa đơn: " + reservation.getId())).setFont(font);
            document.add(new Paragraph("Ngày tạo: " + reservation.getCreatedAt().toString())).setFont(font);
            document.add(new Paragraph("Mã khách hàng: " + reservation.getUser().getId())).setFont(font);
            document.add(new Paragraph("Tên khách hàng: " + reservation.getUser().getUserName())).setFont(font);
            document.add(new Paragraph("Số điện thoại: " + reservation.getUser().getPhoneNumber())).setFont(font);
            document.add(new Paragraph("Email: " + reservation.getUser().getEmail())).setFont(font);
            document.add(new Paragraph("Tên sân: " + reservation.getCourt().getCourtName())).setFont(font);
            document.add(new Paragraph("Loại sân: " + reservation.getCourt().getCourtType().getCourtTypeName())).setFont(font);
            document.add(new Paragraph("Ngày đặt sân: " + reservation.getReservationDate().toString())).setFont(font);
            document.add(new Paragraph("Giờ đến: " + reservation.getCheckInTime() + ":00")).setFont(font);
            document.add(new Paragraph("Giờ ra: " + reservation.getCheckOutTime() + ":00")).setFont(font);
            document.add(new Paragraph("Phương thức thanh toán: " + reservation.getPaymentMethod())).setFont(font);
            document.add(new Paragraph("Trạng thái: " + reservation.getReservationState())).setFont(font);
            document.add(new Paragraph("Tổng cộng: " + reservation.getTotalPrice() + " VND")).setFont(font);

            // Đóng tài liệu
            document.close();

            // Trả về mảng byte PDF
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate invoice", e);
        }
    }
}
