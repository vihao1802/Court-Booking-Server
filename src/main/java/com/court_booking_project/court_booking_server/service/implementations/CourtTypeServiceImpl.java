package com.court_booking_project.court_booking_server.service.Implementations;

import com.court_booking_project.court_booking_server.Mapper.CourtTypeMapper;
import com.court_booking_project.court_booking_server.dto.Request.court_type.CreateCourtTypeRequest;
import com.court_booking_project.court_booking_server.dto.Request.court_type.UpdateCourtTypeRequest;
import com.court_booking_project.court_booking_server.dto.Response.CourtTypeResponse;
import com.court_booking_project.court_booking_server.entity.CourtType;
import com.court_booking_project.court_booking_server.repository.ICourtTypeRepository;
import com.court_booking_project.court_booking_server.service.Interfaces.ICourtTypeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CourtTypeServiceImpl implements ICourtTypeService {
    CourtTypeMapper courtTypeMapper;
    ICourtTypeRepository courtTypeRepository;

    @Override
    public List<CourtTypeResponse> getAll() {
        return courtTypeRepository.findAll().stream().map(courtTypeMapper::convertEntityToDTO).toList();
        // stream is a sequence of elements, perform operations on collections (like lists, sets, etc.), no need to write loops
        // .stream() : manipulate the elements without explicitly writing loops.
    }

    @Override
    public CourtTypeResponse get(String id) {
        return courtTypeRepository.findById(id).map(courtTypeMapper::convertEntityToDTO).orElseThrow(() -> new RuntimeException("Court Type not found"));
    }

    @Override
    public CourtTypeResponse add(CreateCourtTypeRequest request) {
        CourtType courtType = courtTypeMapper.convertCreateDTOtoEntity(request);
        courtTypeRepository.save(courtType);
        return courtTypeMapper.convertEntityToDTO(courtType);
    }

    @Override
    public CourtTypeResponse update(String id, UpdateCourtTypeRequest request) {
        CourtType courtType = courtTypeRepository.findById(id).orElseThrow(() -> new RuntimeException("Court Type not found")); // entity
        courtTypeMapper.convertUpdateDTOtoEntity(courtType, request); // Update the existing entity with values from the request
        courtTypeRepository.save(courtType);
        return courtTypeMapper.convertEntityToDTO(courtType); // entity to DTO
    }

}