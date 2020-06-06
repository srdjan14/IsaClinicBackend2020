package com.ftn.isa.service;

import com.ftn.isa.dto.request.CreateExaminationRequest;
import com.ftn.isa.dto.response.ExaminationRequestResponse;
import com.ftn.isa.dto.response.MedicalStaffResponse;


import java.util.List;

public interface IExaminationRequestService {

    List<ExaminationRequestResponse> getAllExaminationRequest();

    ExaminationRequestResponse createPredefinedExaminationRequest(CreateExaminationRequest request);

    void bookingPredefinedExamination(Long patientId, Long examinationRequestId);

    List<ExaminationRequestResponse> findAllExaminationOfPatient(Long id);

    List<ExaminationRequestResponse> getAllExaminationsByMedical(Long id);
}
