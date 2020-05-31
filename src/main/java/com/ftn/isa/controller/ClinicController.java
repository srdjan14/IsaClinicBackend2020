package com.ftn.isa.controller;

import com.ftn.isa.dto.request.ClinicRequest;
import com.ftn.isa.dto.response.ClinicResponse;
import com.ftn.isa.service.IClinicService;
import com.ftn.isa.service.IPatientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clinics")
public class ClinicController {

    private final IClinicService _clinicService;

    private final IPatientService _patientService;

    public ClinicController(IClinicService clinicService, IPatientService patientService) {
        _clinicService = clinicService;
        _patientService = patientService;
    }

    @GetMapping
    public List<ClinicResponse> getClinics() {
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
}
