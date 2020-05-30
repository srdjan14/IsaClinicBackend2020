package com.ftn.isa.service;

import com.ftn.isa.dto.response.ClinicResponse;
import com.ftn.isa.dto.request.ClinicRequest;

import java.util.List;
import java.util.UUID;

public interface IClinicService {

    ClinicResponse createClinic(ClinicRequest request) throws Exception;

    ClinicResponse updateClinic(UUID id, ClinicRequest request) throws Exception;

    ClinicResponse getClinic(UUID id) throws Exception;

    List<ClinicResponse> getClinics();
}
