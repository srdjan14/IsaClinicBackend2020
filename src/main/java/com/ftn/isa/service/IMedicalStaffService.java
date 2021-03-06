package com.ftn.isa.service;

import com.ftn.isa.dto.request.CreateMedicalStaffRequest;
import com.ftn.isa.dto.request.SearchDoctorForExaminationRequest;
import com.ftn.isa.dto.request.SearchMedicalStaffRequest;
import com.ftn.isa.dto.request.UpdateMedicalStaffRequest;
import com.ftn.isa.dto.response.MedicalStaffResponse;

import java.util.List;

public interface IMedicalStaffService {

    MedicalStaffResponse createMedicalStaff(CreateMedicalStaffRequest request, Long clinicId) throws Exception;

    MedicalStaffResponse updateMedicalStaff(Long id, UpdateMedicalStaffRequest request) throws Exception;

    MedicalStaffResponse getMedicalStaff(Long id) throws Exception;

    List<MedicalStaffResponse> getMedicalStaff();

    List<MedicalStaffResponse> getAllMedicalByClinic(Long id) throws Exception;

    void deleteMedicalStaff(Long id) throws Exception;

    List<MedicalStaffResponse> searchMedicalStaff(SearchMedicalStaffRequest searchMedicalStaffRequest, Long clinicId) throws Exception;

    List<MedicalStaffResponse> searchMedicalExaminationType(SearchMedicalStaffRequest request, Long clinicId, Long examinationTypeId) throws Exception;

    List<MedicalStaffResponse> getMedicalByExaminationType(Long id, Long clinicId);

    List<MedicalStaffResponse> getDoctorsWithAvailableExaminations(SearchDoctorForExaminationRequest request, Long id);
}
