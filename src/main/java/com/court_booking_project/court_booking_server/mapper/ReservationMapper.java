package com.court_booking_project.court_booking_server.mapper;

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
    @Mapping(source = "checkInTime", target = "checkInTime", qualifiedByName = "stringToDateTime")
    @Mapping(source = "checkOutTime", target = "checkOutTime", qualifiedByName = "stringToDateTime")
    @Mapping(source = "reservationDate", target = "reservationDate", qualifiedByName = "stringToDateTime")
//    @Mapping(source = "reservationState", target = "reservationState", qualifiedByName = "intToReservationState")
    Reservation convertCreateDTOtoEntity(CreateReservationRequest request);

    @Mapping(source = "reservationState", target = "reservationState", qualifiedByName = "intToReservationState")
    void convertUpdateDTOtoEntity(@MappingTarget Reservation reservation, UpdateReservationRequest request);

    @Mapping(source = "court.id",target = "courtId")
    @Mapping(source = "user.id",target = "userId")
    ReservationResponse convertEntityToResponse(Reservation entity);

    @Named("stringToDateTime")
    default Date mapStringToDateTime(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(date);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format, expected ISO 8601 format yyyy-MM-dd'T'HH:mm:ss", e);
        }
    }

    @Named("intToReservationState")
    default ReservationState mapIntegerToReservationState(Integer state) {
        return ReservationState.values()[state];
    }
}
