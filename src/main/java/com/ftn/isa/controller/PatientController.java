package com.ftn.isa.controller;

import com.ftn.isa.dto.request.CreatePatientRequest;
import com.ftn.isa.dto.request.SearchPatientRequest;
import com.ftn.isa.dto.request.UpdatePatientRequest;
import com.ftn.isa.dto.response.PatientResponse;
import com.ftn.isa.entity.Patient;
import com.ftn.isa.service.IPatientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/{id}/clinic")
    public List<PatientResponse> getAllMedicalByClinic(@PathVariable Long id) throws Exception {
        return _patientService.getPatientsByClinic(id);
    }

    @GetMapping("/{id}/search")
    public List<PatientResponse> searchPatients(SearchPatientRequest request,@PathVariable Long id) throws Exception {
        return _patientService.searchPatients(request, id);
    }
}
