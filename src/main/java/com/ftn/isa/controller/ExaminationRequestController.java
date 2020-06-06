package com.ftn.isa.controller;

import com.ftn.isa.dto.response.ExaminationRequestResponse;
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

    @PostMapping("/predefined-booking")
    public void bookingExamination(@RequestParam Long patientId, Long clinicId) {
        _examinationRequestService.bookingPredefinedExamination(patientId, clinicId);
    }

    @GetMapping
    public List<ExaminationRequestResponse> getExaminationRequestes() {
        return _examinationRequestService.getAllExaminationRequest();
    }

    @GetMapping("/{id}/patient-examination")
    public List<ExaminationRequestResponse> getPatientsExaminations(@PathVariable Long patientId) {
        return _examinationRequestService.findAllExaminationOfPatient(patientId);
    }
}
