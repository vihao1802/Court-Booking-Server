package com.court_booking_project.court_booking_server.mapper;

import com.court_booking_project.court_booking_server.dto.request.court.CreateCourtRequest;
import com.court_booking_project.court_booking_server.dto.request.court.UpdateCourtRequest;
import com.court_booking_project.court_booking_server.dto.response.court.CourtResponse;
import com.court_booking_project.court_booking_server.entity.Court;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CourtMapper {

    Court convertCreateDTOtoEntity(CreateCourtRequest request);

    void convertUpdateDTOtoEntity(@MappingTarget Court court, UpdateCourtRequest request);

    @Mapping(source = "courtImageList", target = "courtImageList")
    CourtResponse convertEntityToDTO(Court entity) ;
}
