package com.court_booking_project.court_booking_server.service.implementations;

import com.court_booking_project.court_booking_server.dto.request.court.CreateCourtRequest;
import com.court_booking_project.court_booking_server.dto.request.court.UpdateCourtRequest;
import com.court_booking_project.court_booking_server.dto.response.court.CourtResponse;
import com.court_booking_project.court_booking_server.entity.Court;
import com.court_booking_project.court_booking_server.entity.CourtType;
import com.court_booking_project.court_booking_server.mapper.CourtMapper;
import com.court_booking_project.court_booking_server.repository.ICourtRepository;
import com.court_booking_project.court_booking_server.repository.ICourtTypeRepository;
import com.court_booking_project.court_booking_server.service.interfaces.ICourtService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class CourtServiceImpl implements ICourtService {

    ICourtRepository courtRepository;
    ICourtTypeRepository courtTypeRepository;
    CourtMapper courtMapper;

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
        CourtType courtType = courtTypeRepository.findById(request.getCourtTypeId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid court type ID"));

        Court court = courtMapper.convertCreateDTOtoEntity(request);

        court.setCourtType(courtType);

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


}
