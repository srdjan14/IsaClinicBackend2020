package com.ftn.isa.service;

import com.ftn.isa.dto.request.CreateVacationRequest;
import com.ftn.isa.dto.response.VacationRequestResponse;

import java.util.List;

public interface IVacationRequestService {

    VacationRequestResponse createVacationRequest(CreateVacationRequest request);

    VacationRequestResponse getVacationRequest(Long id) throws Exception;

    List<VacationRequestResponse> getVacationRequests();

    void approveVacationRequest(Long id) throws Exception;

    void declineVacationRequest(Long id) throws Exception;

    List<VacationRequestResponse> getAllVacationRequestsByClinic(Long id) throws Exception;
}
