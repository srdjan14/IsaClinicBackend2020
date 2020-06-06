package com.ftn.isa.controller;

import com.ftn.isa.dto.request.ClinicReviewRequest;
import com.ftn.isa.dto.request.DoctorReviewRequest;
import com.ftn.isa.dto.response.ClinicReviewResponse;
import com.ftn.isa.dto.response.DoctorReviewResponse;
import com.ftn.isa.service.IReviewService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/review")
public class ReviewController {

    private final IReviewService _reviewService;

    public ReviewController(IReviewService reviewService) {
        _reviewService = reviewService;
    }

    @PostMapping("/doctor")
    public DoctorReviewResponse reviewDoctor(@RequestBody DoctorReviewRequest request) {
        return _reviewService.reviewingDoctor(request);
    }

    @PostMapping("/clinic")
    public ClinicReviewResponse reviewClinic(@RequestBody ClinicReviewRequest request) {
        return _reviewService.reviewingClinic(request);
    }
}
