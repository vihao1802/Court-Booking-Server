package com.court_booking_project.court_booking_server.service.implementations;

import com.court_booking_project.court_booking_server.constant.CloudinaryFolder;
import com.court_booking_project.court_booking_server.controller.CourtController;
import com.court_booking_project.court_booking_server.constant.ReservationState;
import com.court_booking_project.court_booking_server.dto.request.court.CreateCourtRequest;
import com.court_booking_project.court_booking_server.dto.request.court.UpdateCourtRequest;
import com.court_booking_project.court_booking_server.dto.request.court_image.CourtImageRequest;
import com.court_booking_project.court_booking_server.dto.request.court_image.CreateCourtImageRequest;
import com.court_booking_project.court_booking_server.dto.request.court_image.UpdateCourtImageRequest;
import com.court_booking_project.court_booking_server.dto.response.CloudinaryResponse;
import com.court_booking_project.court_booking_server.dto.response.court.CourtResponse;
import com.court_booking_project.court_booking_server.entity.Court;
import com.court_booking_project.court_booking_server.entity.CourtImage;
import com.court_booking_project.court_booking_server.entity.Reservation;
import com.court_booking_project.court_booking_server.exception.AppException;
import com.court_booking_project.court_booking_server.exception.ErrorCode;
import com.court_booking_project.court_booking_server.mapper.CourtMapper;
import com.court_booking_project.court_booking_server.repository.ICourtImageRepository;
import com.court_booking_project.court_booking_server.repository.ICourtRepository;
import com.court_booking_project.court_booking_server.repository.ICourtTypeRepository;
import com.court_booking_project.court_booking_server.repository.IReservationRepository;
import com.court_booking_project.court_booking_server.service.interfaces.ICourtService;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class CourtServiceImpl implements ICourtService {

    private static final Log log = LogFactory.getLog(CourtServiceImpl.class);
    ICourtRepository courtRepository;
    ICourtTypeRepository courtTypeRepository;
    ICourtImageRepository courtImageRepository;
    IReservationRepository reservationRepository;
    MediaService mediaService;
    CourtMapper courtMapper;

    @Override
    public Page<CourtResponse> getCourtsByType(String typeId,Pageable pageable) {
        return courtRepository.findByCourtTypeIdAndIsDeletedNot(typeId, 1, pageable).map(courtMapper::convertEntityToDTO);
    }

    @Override
    public List<CourtResponse> getAll() {
        return courtRepository.findAll().stream().filter(court -> court.getIsDeleted() != 1).map(courtMapper::convertEntityToDTO).toList();
    }

    @Override
    public CourtResponse get(String id) {
        return courtRepository.findById(id).map(courtMapper::convertEntityToDTO).orElseThrow(() -> new RuntimeException("Court not found"));
    }

    @Override
    public CourtResponse add(CreateCourtRequest request) {
        var courtType = courtTypeRepository.findById(request.getCourtTypeId());

        if(courtType.isEmpty())
            throw new AppException(ErrorCode.INVALID_COURT_TYPE_ID);

        Court court = courtMapper.convertCreateDTOtoEntity(request);
        court.setCourtType(courtType.get());

        courtRepository.save(court);
        return courtMapper.convertEntityToDTO(court);
    }

    @Override
    public CourtResponse update(String id, UpdateCourtRequest request) {
        Court court = courtRepository.findById(id).orElseThrow(() -> new RuntimeException("Court not found"));
        courtMapper.convertUpdateDTOtoEntity(court, request);
        courtRepository.save(court);
        return courtMapper.convertEntityToDTO(court);
    }

    @Override
    @Transactional
    public CourtResponse creatCourtImageList(String courtId, CreateCourtImageRequest requests) {
        Court court = courtRepository.findById(courtId).orElseThrow(() -> new RuntimeException("Court not found"));

        List<CourtImage> imageList = requests.getCourtImages().stream().map(imageRequest -> {
            CloudinaryResponse cloudinaryResponse = mediaService.uploadMedia(imageRequest.getCourtImageSrc(), CloudinaryFolder.court);
            CourtImage courtImage = new CourtImage();
            courtImage.setCourtImageSrc(cloudinaryResponse.getUrl());
            courtImage.setImageType(imageRequest.getImageType());
            courtImage.setCourt(court);
            return courtImage;
        }).collect(Collectors.toList());

        court.setCourtImageList(imageList);
        courtRepository.save(court);
        return courtMapper.convertEntityToDTO(court);
    }
    @Override
    public List<String> getAvailableDate(String courtId) {
        List<String> availableDays = new ArrayList<>();

        LocalDate today = LocalDate.now();
        int lastDayOfRange = 3; // range day is 3 from now

        for (int i = 0; i <= lastDayOfRange; i++) {
            LocalDate date = today.plusDays(i);
            if(checkAvailableDate(date,courtId)) {
                availableDays.add(date.toString());
            }
        }

        return availableDays;
    }

    private List<String> calculateAvailableHours(LocalDate date, String courtId) {
        List<String> availableHours = new ArrayList<>();
        for (int i = 8; i <= 24; i++) {
            availableHours.add(String.valueOf(i));
        }

        List<ReservationState> states = Arrays.asList(ReservationState.PENDING, ReservationState.SUCCESS);
        List<Reservation> reservations = reservationRepository.findByReservationDateAndCourtIdAndReservationStates(date, courtId, states);

        for (Reservation res : reservations) {
            int checkIn = Integer.parseInt(res.getCheckInTime());
            int checkOut = Integer.parseInt(res.getCheckOutTime());

            for (int i = checkIn; i < checkOut; i++) {
                availableHours.remove(String.valueOf(i));
            }
        }

        return availableHours;
    }

    public boolean checkAvailableDate(LocalDate date, String courtId) {
        List<String> availableHours = calculateAvailableHours(date, courtId);
        return !availableHours.isEmpty();
    }

    public List<String> getAvailableHours(String courtId,String date) {
        LocalDate parsedDate = LocalDate.parse(date);
        return calculateAvailableHours(parsedDate, courtId);
    }

    public List<String> getUnavailableHours(String courtId, String date) {
        LocalDate parsedDate = LocalDate.parse(date);

        List<ReservationState> states = Arrays.asList(ReservationState.PENDING, ReservationState.SUCCESS);
        List<Reservation> reservations = reservationRepository.findByReservationDateAndCourtIdAndReservationStates(parsedDate, courtId, states);

        Set<String> notAvailableHoursSet = new HashSet<>();

        for (Reservation res : reservations) {
            int checkIn = Integer.parseInt(res.getCheckInTime());
            int checkOut = Integer.parseInt(res.getCheckOutTime());

            for (int i = checkIn; i <= checkOut; i++) {
                notAvailableHoursSet.add(String.valueOf(i));
            }
        }

        return new ArrayList<>(notAvailableHoursSet);

    }

    public Page<CourtResponse> getAvailableCourtsByTypeAndDateTime(String typeId, LocalDate date, int start, int end, Pageable pageable) {
        Page<Court> availableCourts = courtRepository.getAvailableCourtsByTypeAndDateTime(typeId, date, start, end, pageable);

        return availableCourts.map(courtMapper::convertEntityToDTO);
    }

    public CourtResponse deleteCourt(String id) {
        Court court = courtRepository.findById(id).orElseThrow(() -> new RuntimeException("Court not found"));
        court.setIsDeleted(1);
        courtRepository.save(court);
        return courtMapper.convertEntityToDTO(court);
    }


}
