package com.court_booking_project.court_booking_server.mapper;

import com.court_booking_project.court_booking_server.dto.request.court_image.CreateCourtImageRequest;
import com.court_booking_project.court_booking_server.dto.request.court_image.UpdateCourtImageRequest;
import com.court_booking_project.court_booking_server.dto.response.courtImage.CourtImageResponse;
import com.court_booking_project.court_booking_server.entity.CourtImage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CourtImageMapper {
    CourtImage convertCreateDTOtoEntity(CreateCourtImageRequest request);

    void convertUpdateDTOtoEntity(@MappingTarget CourtImage courtImage, UpdateCourtImageRequest request);

//    @Mapping(source = "court.id",target = "courtId")
    CourtImageResponse convertEntityToDTO(CourtImage courtImage);
}
