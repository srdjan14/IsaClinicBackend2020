package com.ftn.isa.controller;

import com.ftn.isa.dto.request.CreatePatientRequest;
import com.ftn.isa.dto.request.UpdatePatientRequest;
import com.ftn.isa.dto.response.PatientResponse;
import com.ftn.isa.service.IPatientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patients")
public class PatientController {

    private final IPatientService _patientService;

    public PatientController(IPatientService patientService) {
        _patientService = patientService;
    }

    @PutMapping("/{id}")
    public PatientResponse updatePatient(@PathVariable Long id, @RequestBody UpdatePatientRequest request) {
        try {
            return _patientService.updatePatient(id, request);
        } catch (Exception ex) {
            return null;
        }
    }

    @GetMapping("/{id}")
    public PatientResponse getPatient(@PathVariable Long id) {
        try {
            return _patientService.getPatient(id);
        } catch (Exception ex) {
            return null;
        }
    }

    @GetMapping
    public List<PatientResponse> getPatients() {
        return _patientService.getPatients();
    }

    @PutMapping("/delete/{id}")
    public void deletePatient(@PathVariable Long id) {
        _patientService.deletePatient(id);
    }
}
