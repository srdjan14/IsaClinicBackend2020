package com.ftn.isa.service;

import com.ftn.isa.dto.request.SearchDoctorForExaminationRequest;
import com.ftn.isa.dto.response.ClinicResponse;
import com.ftn.isa.dto.request.ClinicRequest;
import com.ftn.isa.dto.response.MedicalStaffResponse;

import java.util.List;

public interface IClinicService {

    ClinicResponse createClinic(ClinicRequest request) throws Exception;

    ClinicResponse updateClinic(Long id, ClinicRequest request) throws Exception;

    ClinicResponse getClinic(Long id) throws Exception;

    List<ClinicResponse> getClinics();

    List<ClinicResponse> searchClinic(ClinicRequest request);

    List<ClinicResponse> searchFreeDoctorInClinic(SearchDoctorForExaminationRequest request) throws Exception;
}
