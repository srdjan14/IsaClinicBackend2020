package com.ftn.isa.service.implementation;

import com.ftn.isa.dto.request.ExaminationTypeRequest;
import com.ftn.isa.dto.response.ExaminationTypeResponse;
import com.ftn.isa.entity.ExaminationType;
import com.ftn.isa.repository.ExaminationTypeRepository;
import com.ftn.isa.service.IExaminationTypeService;
import com.ftn.isa.utils.enums.DeletedStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExaminationTypeService implements IExaminationTypeService {

    private final ExaminationTypeRepository _examinationTypeRepository;

    public ExaminationTypeService(ExaminationTypeRepository examinationTypeRepository) {
        _examinationTypeRepository = examinationTypeRepository;
    }

    @Override
    public ExaminationTypeResponse createExaminationType(ExaminationTypeRequest request) throws Exception {
        ExaminationType examinationType = new ExaminationType();
        examinationType.setPrice(request.getPrice());
        examinationType.setName(request.getName());

        ExaminationType savedExaminationType = _examinationTypeRepository.save(examinationType);
        return mapExaminationTypeToExaminationTypeResponse(savedExaminationType);
    }

    @Override
    public ExaminationTypeResponse updateExaminationType(ExaminationTypeRequest request, Long id) throws Exception {
        ExaminationType examinationType = _examinationTypeRepository.findOneById(id);
        examinationType.setPrice(request.getPrice());
        examinationType.setName(request.getName());

        ExaminationType savedExaminationType = _examinationTypeRepository.save(examinationType);
        return mapExaminationTypeToExaminationTypeResponse(savedExaminationType);
    }

    @Override
    public ExaminationTypeResponse getExaminationType(Long id) throws Exception {
        ExaminationType examinationType = _examinationTypeRepository.findOneById(id);

        if (examinationType == null) {
            throw new Exception(String.format("Speciality with %s id is not found", id));
        }

        return mapExaminationTypeToExaminationTypeResponse(examinationType);
    }

    @Override
    public List<ExaminationTypeResponse> getAllExaminationType() {
        List<ExaminationType> examinations = _examinationTypeRepository.findAll();

        return examinations
                .stream()
                .map(examinationType -> mapExaminationTypeToExaminationTypeResponse(examinationType))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteExaminationType(Long id) {
        ExaminationType examinationType = _examinationTypeRepository.findOneById(id);
        examinationType.setDeletedStatus(DeletedStatus.IS_DELETED);
        _examinationTypeRepository.save(examinationType);
    }

    public ExaminationTypeResponse mapExaminationTypeToExaminationTypeResponse(ExaminationType examinationType){
        ExaminationTypeResponse examinationTypeResponse = new ExaminationTypeResponse();
        examinationTypeResponse.setId(examinationType.getId());
        examinationTypeResponse.setName(examinationType.getName());
        examinationTypeResponse.setPrice(examinationType.getPrice());
        return examinationTypeResponse;
    }
}
