package com.ftn.isa.controller;

import com.ftn.isa.dto.request.CreatePatientRequest;
import com.ftn.isa.dto.request.FirstLoginPasswordRequest;
import com.ftn.isa.dto.request.LoginRequest;
import com.ftn.isa.dto.response.LoginResponse;
import com.ftn.isa.dto.response.PatientResponse;
import com.ftn.isa.service.IPatientService;
import com.ftn.isa.service.implementation.AuthService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService _authService;

    private final IPatientService _patientService;

    public AuthController(AuthService authService, IPatientService patientService) {
        _authService = authService;
        _patientService = patientService;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) throws Exception {
        return _authService.login(request);
    }

    @PostMapping("/medical/{id}/first-password")
    public LoginResponse loginFirstPassword(@PathVariable Long id, @RequestBody FirstLoginPasswordRequest request) {
        try {
            return _authService.changingDefaultPassword(id, request);
        } catch (Exception ex) {
            return null;
        }
    }

    @PostMapping("/patients")
    public PatientResponse createPatient(@RequestBody CreatePatientRequest request) throws Exception {
        return _patientService.createPatient(request);
    }
}
