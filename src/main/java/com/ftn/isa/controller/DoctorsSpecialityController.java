package com.ftn.isa.controller;

import com.ftn.isa.dto.request.DoctorsSpecialityRequest;
import com.ftn.isa.dto.response.DoctorsSpecialityResponse;
import com.ftn.isa.repository.DoctorsSpecialityRepository;
import com.ftn.isa.service.IDoctorsSpecialityService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/speciality")
public class DoctorsSpecialityController {

    private final IDoctorsSpecialityService _doctorsSpecialityService;

    private final DoctorsSpecialityRepository _doctorsSpecialityRepository;

    public DoctorsSpecialityController(IDoctorsSpecialityService doctorsSpecialityService, DoctorsSpecialityRepository doctorsSpecialityRepository) {
        _doctorsSpecialityService = doctorsSpecialityService;
        _doctorsSpecialityRepository = doctorsSpecialityRepository;
    }

    @PostMapping
    public DoctorsSpecialityResponse createSpeciality(@RequestBody DoctorsSpecialityRequest request) throws Exception {

        return _doctorsSpecialityService.createSpeciality(request);
    }

    @GetMapping("/{id}")
    public DoctorsSpecialityResponse getSpeciality(@PathVariable Long id) throws Exception {
        return _doctorsSpecialityService.getSpeciality(id);
    }

    @PutMapping("/{id}")
    public DoctorsSpecialityResponse updateSpeciality(@RequestBody DoctorsSpecialityRequest request, @PathVariable Long id) throws Exception {
        return _doctorsSpecialityService.updateSpeciality(request, id);
    }

}
