package com.ftn.isa.controller;

import com.ftn.isa.dto.response.RegistartionRequestResponse;
import com.ftn.isa.service.IRegistrationRequestService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/registration-requests")
public class RegistrationRequestController {

    private IRegistrationRequestService _registrationRequestService;

    public RegistrationRequestController(IRegistrationRequestService registrationRequestService) {
        _registrationRequestService = registrationRequestService;
    }

    @GetMapping
    public List<RegistartionRequestResponse> getAll() {
        return _registrationRequestService.getAll();
    }

    @PutMapping("/{id}/approve")
    public void approveRegistrationRequest(@PathVariable Long id) throws Exception {
        _registrationRequestService.approveRegistrationRequest(id);
    }

    @PutMapping("/{id}/decline")
    public void declineRegistrationRequest(@PathVariable Long id) throws Exception {
        _registrationRequestService.declineRegistrationRequest(id);
    }
}
