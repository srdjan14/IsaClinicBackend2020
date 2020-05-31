package com.ftn.isa.service;

import com.ftn.isa.dto.request.CreatePatientRequest;
import com.ftn.isa.dto.request.UpdatePatientRequest;
import com.ftn.isa.dto.response.PatientResponse;

import java.util.List;
import java.util.UUID;

public interface IPatientService {

    PatientResponse createPatient(CreatePatientRequest request) throws Exception;

    PatientResponse updatePatient(Long id, UpdatePatientRequest request) throws Exception;

    PatientResponse getPatient(Long id) throws Exception;

    List<PatientResponse> getPatients();

    void deletePatient(Long id);
}
