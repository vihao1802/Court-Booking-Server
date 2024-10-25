package com.court_booking_project.court_booking_server.mapper;

import com.court_booking_project.court_booking_server.dto.Request.court_type.CreateCourtTypeRequest;
import com.court_booking_project.court_booking_server.dto.Request.court_type.UpdateCourtTypeRequest;
import com.court_booking_project.court_booking_server.dto.response.CourtTypeResponse;
import com.court_booking_project.court_booking_server.entity.CourtType;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CourtTypeMapper {

    CourtType convertCreateDTOtoEntity(CreateCourtTypeRequest request);

    void convertUpdateDTOtoEntity(@MappingTarget CourtType courtType , UpdateCourtTypeRequest request);

    CourtTypeResponse convertEntityToDTO(CourtType entity);
}
