package com.ftn.isa.controller;

import com.ftn.isa.dto.request.ExaminationTypeRequest;
import com.ftn.isa.dto.response.ExaminationTypeResponse;
import com.ftn.isa.repository.ExaminationTypeRepository;
import com.ftn.isa.service.IExaminationTypeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/examination-type")
public class ExaminationTypeController {

    private final IExaminationTypeService _examinationTypeService;

    private final ExaminationTypeRepository _examinationTypeRepository;

    public ExaminationTypeController(IExaminationTypeService examinationTypeService, ExaminationTypeRepository examinationTypeRepository) {
        _examinationTypeService = examinationTypeService;
        _examinationTypeRepository = examinationTypeRepository;
    }

    @PostMapping
    public ExaminationTypeResponse createExaminationType(@RequestBody ExaminationTypeRequest request) throws Exception {

        return _examinationTypeService.createExaminationType(request);
    }

    @GetMapping("/{id}")
    public ExaminationTypeResponse getExaminationType(@PathVariable Long id) throws Exception {
        return _examinationTypeService.getExaminationType(id);
    }

    @PutMapping("/{id}")
    public ExaminationTypeResponse updateSpeciality(@RequestBody ExaminationTypeRequest request, @PathVariable Long id) throws Exception {
        return _examinationTypeService.updateExaminationType(request, id);
    }

    @GetMapping
    public List<ExaminationTypeResponse> getAllExaminationType() {
        return _examinationTypeService.getAllExaminationType();
    }

    @PutMapping("/delete/{id}")
    public void deleteExaminationType(@PathVariable Long id) {
        _examinationTypeService.deleteExaminationType(id);
    }
}
