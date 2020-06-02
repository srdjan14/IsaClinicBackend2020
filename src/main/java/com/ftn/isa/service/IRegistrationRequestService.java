package com.ftn.isa.service;

import com.ftn.isa.dto.request.CreatePatientRequest;
import com.ftn.isa.dto.response.RegistartionRequestResponse;

import java.util.List;

public interface IRegistrationRequestService {

    List<RegistartionRequestResponse> getAll();

    void approveRegistrationRequest(Long id) throws Exception;

    void declineRegistrationRequest(Long id) throws Exception;

    void createRegistrationRequest(CreatePatientRequest request);
}
