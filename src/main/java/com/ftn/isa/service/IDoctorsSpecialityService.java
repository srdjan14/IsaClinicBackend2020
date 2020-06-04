package com.ftn.isa.service;

import com.ftn.isa.dto.request.DoctorsSpecialityRequest;
import com.ftn.isa.dto.response.DoctorsSpecialityResponse;

import java.util.List;

public interface IDoctorsSpecialityService {

    DoctorsSpecialityResponse createSpeciality(DoctorsSpecialityRequest request) throws Exception;

    DoctorsSpecialityResponse updateSpeciality(DoctorsSpecialityRequest request, Long id) throws Exception;

    DoctorsSpecialityResponse getSpeciality(Long id) throws Exception;

    List<DoctorsSpecialityResponse> getSpecialities();

    void deleteSpeciality(Long id);
}
