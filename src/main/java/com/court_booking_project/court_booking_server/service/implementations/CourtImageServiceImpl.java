package com.court_booking_project.court_booking_server.service.implementations;

import com.court_booking_project.court_booking_server.constant.CloudinaryFolder;
import com.court_booking_project.court_booking_server.dto.request.court_image.CourtImageFileRequest;
import com.court_booking_project.court_booking_server.dto.request.court_image.CourtImageRequest;
import com.court_booking_project.court_booking_server.dto.request.court_image.UpdateCourtImageRequest;
import com.court_booking_project.court_booking_server.dto.response.CloudinaryResponse;
import com.court_booking_project.court_booking_server.dto.response.courtImage.CourtImageResponse;
import com.court_booking_project.court_booking_server.entity.CourtImage;
import com.court_booking_project.court_booking_server.mapper.CourtImageMapper;
import com.court_booking_project.court_booking_server.repository.ICourtImageRepository;
import com.court_booking_project.court_booking_server.repository.ICourtRepository;
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
    ICourtRepository courtRepository;
    MediaService mediaService;

    @Override
    public List<CourtImageResponse> getAll() {
        return courtImageRepository.findAll().stream().map(courtImageMapper::convertEntityToDTO).toList();
    }

    @Override
    public CourtImageResponse get(String courtId) {
        return courtImageRepository.findById(courtId).map(courtImageMapper::convertEntityToDTO).orElseThrow(() -> new RuntimeException("CourtImage not found"));

    }

    @Override
    public CourtImageResponse update(String id, CourtImageFileRequest request) {
        CourtImage currentImage = courtImageRepository.findById(id).orElseThrow(() -> new RuntimeException("CourtImage not found"));

        String url = currentImage.getCourtImageSrc();
        int index = url.indexOf("court/");
        String publicId = url.substring(index);
        mediaService.deleteMedia(publicId);

        CloudinaryResponse cloudinaryResponse = mediaService.uploadMedia(request.getCourtImageSrc(), CloudinaryFolder.court);
        currentImage.setCourtImageSrc(cloudinaryResponse.getUrl());
        currentImage.setImageType(currentImage.getImageType());

        courtImageRepository.save(currentImage);
        return courtImageMapper.convertEntityToDTO(currentImage);
    }
}
