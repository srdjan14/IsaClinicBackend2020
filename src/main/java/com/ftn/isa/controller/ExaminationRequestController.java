package com.ftn.isa.controller;

import com.ftn.isa.dto.request.CreateAvailableExaminationsRequest;
import com.ftn.isa.dto.request.CreateExaminationRequest;
import com.ftn.isa.dto.request.SearchDoctorForExaminationRequest;
import com.ftn.isa.dto.request.SearchExaminationRequest;
import com.ftn.isa.dto.response.ExaminationRequestResponse;
import com.ftn.isa.dto.response.PredefinedExaminationResponse;
import com.ftn.isa.service.IExaminationRequestService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/examination-request")
public class ExaminationRequestController {

    private final IExaminationRequestService _examinationRequestService;

    public ExaminationRequestController(IExaminationRequestService examinationRequestService) {
        _examinationRequestService = examinationRequestService;
    }

    @PostMapping("/create")
    public PredefinedExaminationResponse createPredefinedExamination(@RequestBody CreateExaminationRequest examinationRequest) throws Exception {
        return _examinationRequestService.createPredefinedExaminationRequest(examinationRequest);
    }

    @PostMapping("/predefined-booking/{patientId}/{examinationRequestId}")
    public void bookingExamination(@PathVariable Long patientId, @PathVariable Long examinationRequestId) {
        _examinationRequestService.bookingPredefinedExamination(patientId, examinationRequestId);
    }

    @GetMapping
    public List<ExaminationRequestResponse> getExaminationRequestes() {
        return _examinationRequestService.getAllExaminationRequest();
    }

    @GetMapping("/{id}/patient-examination")
    public List<ExaminationRequestResponse> getPatientsExaminations(@PathVariable("id") Long patientId) {
        return _examinationRequestService.findAllExaminationOfPatient(patientId);
    }

    @GetMapping("/{id}/doctor-examination")
    public List<ExaminationRequestResponse> getMedicalExaminations(@PathVariable("id") Long medicalStaffId) {
        return _examinationRequestService.getAllExaminationsByMedical(medicalStaffId);
    }

    @GetMapping("/search")
    public List<ExaminationRequestResponse> searchExaminations(SearchExaminationRequest request) throws Exception {
        return _examinationRequestService.searchExaminationRequest(request);
    }

    @PostMapping("/available-examinations")
    public ExaminationRequestResponse createAvailableExaminations(@RequestBody CreateAvailableExaminationsRequest examinationRequest) throws Exception {
        return _examinationRequestService.createAvailableExaminations(examinationRequest);
    }

    @GetMapping("/{id}/clinic")
    public List<ExaminationRequestResponse> getAllByClinic(@PathVariable Long id) {
        return _examinationRequestService.getAllByClinic(id);
    }

    @PostMapping("/available/{id}/doctor")
    public List<ExaminationRequestResponse> getAvailableExaminationsOfDoctor(@RequestBody SearchDoctorForExaminationRequest request, @PathVariable Long id) {
        return _examinationRequestService.getAvailableExaminationsOfDoctor(request, id);
    }
}
