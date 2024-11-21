package com.court_booking_project.court_booking_server.mapper;

import com.court_booking_project.court_booking_server.constant.PaymentMethod;
import com.court_booking_project.court_booking_server.constant.ReservationState;
import com.court_booking_project.court_booking_server.dto.request.reservation.CreateReservationRequest;
import com.court_booking_project.court_booking_server.dto.request.reservation.UpdateReservationRequest;
import com.court_booking_project.court_booking_server.dto.response.reservation.ReservationResponse;
import com.court_booking_project.court_booking_server.entity.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Mapper(componentModel = "spring")
public interface ReservationMapper {
    @Mapping(source = "reservationDate", target = "reservationDate", qualifiedByName = "stringToDateTime")
    Reservation convertCreateDTOtoEntity(CreateReservationRequest request);

    @Mapping(source = "reservationState", target = "reservationState", qualifiedByName = "intToReservationState")
    @Mapping(source = "paymentMethod", target = "paymentMethod", qualifiedByName = "stringToPaymentMethod")
    void convertUpdateDTOtoEntity(@MappingTarget Reservation reservation, UpdateReservationRequest request);

    @Mapping(source = "court",target = "court")
    @Mapping(source = "user",target = "user")
    ReservationResponse convertEntityToResponse(Reservation entity);

    @Named("stringToDateTime")
    default Date mapStringToDateTime(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format, expected ISO 8601 format yyyy-MM-dd", e);
        }
    }

    @Named("intToReservationState")
    default ReservationState intToReservationState(Integer stateCode) {
        return ReservationState.fromCode(stateCode);
    }

    @Named("stringToPaymentMethod")
    default PaymentMethod stringToPaymentMethod(String paymentMethod) {
        return PaymentMethod.fromString(paymentMethod);
    }
}
