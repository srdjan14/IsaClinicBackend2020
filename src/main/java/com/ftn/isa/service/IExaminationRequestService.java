package com.ftn.isa.service;

import com.ftn.isa.dto.request.CreateExaminationRequest;
import com.ftn.isa.dto.response.ExaminationRequestResponse;


import java.util.List;
import java.util.UUID;

public interface IExaminationRequestService {

    List<ExaminationRequestResponse> getAllExaminationRequest();

    void createExaminationRequest(CreateExaminationRequest request);

}
