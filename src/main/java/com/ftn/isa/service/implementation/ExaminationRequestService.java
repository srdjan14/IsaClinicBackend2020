package com.ftn.isa.service.implementation;

import com.ftn.isa.dto.request.CreateExaminationRequest;
import com.ftn.isa.dto.response.ExaminationRequestResponse;
import com.ftn.isa.service.IExaminationRequestService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExaminationRequestService implements IExaminationRequestService {

    @Override
    public List<ExaminationRequestResponse> getAllExaminationRequest() {
        return null;
    }

    @Override
    public void createExaminationRequest(CreateExaminationRequest request) {

    }

}
