package com.ftn.isa.service;

import com.ftn.isa.dto.request.CreateAvailableExaminationsRequest;
import com.ftn.isa.dto.request.CreateExaminationRequest;
import com.ftn.isa.dto.request.SearchDoctorForExaminationRequest;
import com.ftn.isa.dto.request.SearchExaminationRequest;
import com.ftn.isa.dto.response.ExaminationRequestResponse;
import com.ftn.isa.dto.response.PredefinedExaminationResponse;

import java.util.List;

public interface IExaminationRequestService {

    List<ExaminationRequestResponse> getAllExaminationRequest();

    PredefinedExaminationResponse createPredefinedExaminationRequest(CreateExaminationRequest request) throws Exception;

    void bookingPredefinedExamination(Long patientId, Long examinationRequestId);

    List<ExaminationRequestResponse> findAllExaminationOfPatient(Long id);

    List<ExaminationRequestResponse> getAllExaminationsByMedical(Long id);

    List<ExaminationRequestResponse> searchExaminationRequest(SearchExaminationRequest request) throws Exception;

    ExaminationRequestResponse createAvailableExaminations(CreateAvailableExaminationsRequest request) throws Exception;

    List<ExaminationRequestResponse> getAllByClinic(Long id);

    List<ExaminationRequestResponse> getAvailableExaminationsOfDoctor(SearchDoctorForExaminationRequest request, Long id);

    void bookingAvailableExamination(Long patientId, Long examinationRequestId);
}
