package com.ftn.isa.service;

import com.ftn.isa.dto.response.ClinicResponse;
import com.ftn.isa.dto.request.ClinicRequest;

import java.util.List;
import java.util.UUID;

public interface IClinicService {

    ClinicResponse createClinic(ClinicRequest request) throws Exception;

    ClinicResponse updateClinic(Long id, ClinicRequest request) throws Exception;

    ClinicResponse getClinic(Long id) throws Exception;

    List<ClinicResponse> getClinics();
}