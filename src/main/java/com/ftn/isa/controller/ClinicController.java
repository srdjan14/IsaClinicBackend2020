package com.ftn.isa.controller;

import com.ftn.isa.dto.request.ClinicRequest;
import com.ftn.isa.dto.response.ClinicResponse;
import com.ftn.isa.dto.response.MedicalStaffResponse;
import com.ftn.isa.service.IClinicService;
import com.ftn.isa.service.IMedicalStaffService;
import com.ftn.isa.service.IPatientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clinics")
public class ClinicController {

    private final IClinicService _clinicService;

    private final IPatientService _patientService;

    private final IMedicalStaffService _medicalStaffService;


    public ClinicController(IClinicService clinicService, IPatientService patientService, IMedicalStaffService medicalStaffService) {
        _clinicService = clinicService;
        _patientService = patientService;
        _medicalStaffService = medicalStaffService;
    }

    @GetMapping
    public List<ClinicResponse> getClinicsD() {
        return _clinicService.getClinics();
    }

    @GetMapping("/{id}")
    public ClinicResponse getClinic(@PathVariable Long id) throws Exception {
        return _clinicService.getClinic(id);
    }

    @PostMapping
    public ClinicResponse createClinic(@RequestBody ClinicRequest request) {
        try {
            return _clinicService.createClinic(request);
        } catch (Exception ex) {
            return null;
        }
    }

    @PutMapping("/{id}")
    public ClinicResponse updateClinic(@PathVariable Long id, @RequestBody ClinicRequest request) {
        try {
            return _clinicService.updateClinic(id, request);
        } catch (Exception ex) {
            return null;
        }
    }

    @GetMapping("/{id}/medical")
    public List<MedicalStaffResponse> getAllMedicalByClinic(@PathVariable Long id) throws Exception {
        return _medicalStaffService.getAllMedicalByClinic(id);
    }

    @GetMapping("/search")
    public List<ClinicResponse> searchClinic(ClinicRequest request) {
        return _clinicService.searchClinic(request);
    }
}
