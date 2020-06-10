package com.ftn.isa.controller;

import com.ftn.isa.dto.request.CreateVacationRequest;
import com.ftn.isa.dto.response.VacationRequestResponse;
import com.ftn.isa.service.IVacationRequestService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vacation-request")
public class VacationRequestController {

    private final IVacationRequestService _vacationRequestService;

    public VacationRequestController(IVacationRequestService vacationRequestService) {
        _vacationRequestService = vacationRequestService;
    }

    @GetMapping
    public List<VacationRequestResponse> getVacationRequests() {
        return _vacationRequestService.getVacationRequests();
    }

    @GetMapping("/{id}")
    public VacationRequestResponse getVacationRequest(@PathVariable Long id) throws Exception {
        return _vacationRequestService.getVacationRequest(id);
    }

    @GetMapping("/{id}/clinic")
    public List<VacationRequestResponse> getVacationRequestsByClinic(@PathVariable("id") Long clinicId) throws Exception {
        return _vacationRequestService.getAllVacationRequestsByClinic(clinicId);
    }

    @PostMapping
    public VacationRequestResponse createvacationRequest(@RequestBody CreateVacationRequest request) {
        return _vacationRequestService.createVacationRequest(request);
    }
}
