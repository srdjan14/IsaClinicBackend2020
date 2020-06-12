package com.ftn.isa.service;

import com.ftn.isa.dto.request.CreateExaminationRequest;
import com.ftn.isa.dto.request.SearchExaminationRequest;
import com.ftn.isa.dto.response.ExaminationRequestResponse;

import java.util.List;

public interface IExaminationRequestService {

    List<ExaminationRequestResponse> getAllExaminationRequest();

    ExaminationRequestResponse createPredefinedExaminationRequest(CreateExaminationRequest request);

    void bookingPredefinedExamination(Long patientId, Long examinationRequestId);

    List<ExaminationRequestResponse> findAllExaminationOfPatient(Long id);

    List<ExaminationRequestResponse> getAllExaminationsByMedical(Long id);

    List<ExaminationRequestResponse> searchExaminationRequest(SearchExaminationRequest request) throws Exception;
}
