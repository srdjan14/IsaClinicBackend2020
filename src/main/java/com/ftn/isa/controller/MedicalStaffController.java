package com.ftn.isa.controller;

import com.ftn.isa.dto.request.CreateMedicalStaffRequest;

import com.ftn.isa.dto.request.UpdateMedicalStaffRequest;
import com.ftn.isa.dto.response.MedicalStaffResponse;
import com.ftn.isa.service.IMedicalStaffService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicalStaff")
public class MedicalStaffController {

    private final IMedicalStaffService _medicalStaffService;

    public MedicalStaffController(IMedicalStaffService medicalStaffService) {
        _medicalStaffService = medicalStaffService;
    }

    @PostMapping("/{id}")
    public MedicalStaffResponse createMedicalStaff(@RequestBody CreateMedicalStaffRequest request, @PathVariable Long id){
        try {
            return  _medicalStaffService.createMedicalStaff(request, id);
        } catch (Exception ex) {
            return null;
        }
    }

    @PutMapping("/{id}")
    public MedicalStaffResponse updateMedicalStaff(@PathVariable Long id, @RequestBody UpdateMedicalStaffRequest request) {
        try {
            return _medicalStaffService.updateMedicalStaff(id, request);
        } catch (Exception ex) {
            return null;
        }
    }

    @GetMapping("/{id}")
    public MedicalStaffResponse getMedicalStaff(@PathVariable Long id) {
        try {
            return _medicalStaffService.getMedicalStaff(id);
        } catch (Exception ex) {
            return null;
        }
    }

    @GetMapping
    public List<MedicalStaffResponse> getMedicalStaff() {
        return _medicalStaffService.getMedicalStaff();
    }

    @PutMapping("/delete/{id}")
    public void deleteMedicalStaff(@PathVariable Long id) {
        _medicalStaffService.deleteMedicalStaff(id);
    }
}