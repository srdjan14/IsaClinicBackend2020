package com.ftn.isa.service;

import com.ftn.isa.dto.request.ExaminationTypeRequest;
import com.ftn.isa.dto.response.ExaminationTypeResponse;

import java.util.List;

public interface IExaminationTypeService {

    ExaminationTypeResponse createExaminationType(ExaminationTypeRequest request) throws Exception;

    ExaminationTypeResponse updateExaminationType(ExaminationTypeRequest request, Long id) throws Exception;

    ExaminationTypeResponse getExaminationType(Long id) throws Exception;

    List<ExaminationTypeResponse> getAllExaminationType();

    void deleteExaminationType(Long id);
}
