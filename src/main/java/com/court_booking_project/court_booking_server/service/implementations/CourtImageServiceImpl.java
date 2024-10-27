package com.court_booking_project.court_booking_server.service.implementations;

import com.court_booking_project.court_booking_server.dto.request.court_image.UpdateCourtImageRequest;
import com.court_booking_project.court_booking_server.dto.response.courtImage.CourtImageResponse;
import com.court_booking_project.court_booking_server.entity.CourtImage;
import com.court_booking_project.court_booking_server.mapper.CourtImageMapper;
import com.court_booking_project.court_booking_server.repository.ICourtImageRepository;
import com.court_booking_project.court_booking_server.service.interfaces.ICourtImageService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class CourtImageServiceImpl implements ICourtImageService {
    ICourtImageRepository courtImageRepository;
    CourtImageMapper courtImageMapper;

    @Override
    public List<CourtImageResponse> getAll() {
        return courtImageRepository.findAll().stream().map(courtImageMapper::convertEntityToDTO).toList();
    }

    @Override
    public CourtImageResponse get(String courtId) {
        return courtImageRepository.findById(courtId).map(courtImageMapper::convertEntityToDTO).orElseThrow(() -> new RuntimeException("CourtImage not found"));
    }

    /* @Override
    public CourtImageResponse add(CreateCourtImageRequest request) {
        Court court = courtRepository.findById(request.getCourtId()).orElseThrow(() -> new RuntimeException("Court not found"));

        CourtImage courtImage = courtImageMapper.convertCreateDTOtoEntity(request);

        courtImage.setCourt(court);

        courtImageRepository.save(courtImage);
        return courtImageMapper.convertEntityToDTO(courtImage);
    }*/

    @Override
    public CourtImageResponse update(String id, UpdateCourtImageRequest request) {
        CourtImage courtImage = courtImageRepository.findById(id).orElseThrow(() -> new RuntimeException("CourtImage not found"));
        courtImageMapper.convertUpdateDTOtoEntity(courtImage,request);
        courtImageRepository.save(courtImage);
        return courtImageMapper.convertEntityToDTO(courtImage);
    }
}
