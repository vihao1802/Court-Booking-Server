package com.court_booking_project.court_booking_server.service.implementations;

import com.court_booking_project.court_booking_server.constant.CloudinaryFolder;
import com.court_booking_project.court_booking_server.controller.CourtController;
import com.court_booking_project.court_booking_server.dto.request.court.CreateCourtRequest;
import com.court_booking_project.court_booking_server.dto.request.court.UpdateCourtRequest;
import com.court_booking_project.court_booking_server.dto.request.court_image.CourtImageRequest;
import com.court_booking_project.court_booking_server.dto.request.court_image.CreateCourtImageRequest;
import com.court_booking_project.court_booking_server.dto.request.court_image.UpdateCourtImageRequest;
import com.court_booking_project.court_booking_server.dto.response.CloudinaryResponse;
import com.court_booking_project.court_booking_server.dto.response.court.CourtResponse;
import com.court_booking_project.court_booking_server.entity.Court;
import com.court_booking_project.court_booking_server.entity.CourtImage;
import com.court_booking_project.court_booking_server.exception.AppException;
import com.court_booking_project.court_booking_server.exception.ErrorCode;
import com.court_booking_project.court_booking_server.mapper.CourtMapper;
import com.court_booking_project.court_booking_server.repository.ICourtImageRepository;
import com.court_booking_project.court_booking_server.repository.ICourtRepository;
import com.court_booking_project.court_booking_server.repository.ICourtTypeRepository;
import com.court_booking_project.court_booking_server.service.interfaces.ICourtService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    CourtMapper courtMapper;
    MediaService mediaService;

    @Override
    public Page<CourtResponse> getCourtsByType(String typeId,Pageable pageable) {
        return courtRepository.findByCourtTypeId(typeId, pageable).map(courtMapper::convertEntityToDTO);
    }

    @Override
    public List<CourtResponse> getAll() {
        return courtRepository.findAll().stream().map(courtMapper::convertEntityToDTO).toList();
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
}
