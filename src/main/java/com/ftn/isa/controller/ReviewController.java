package com.ftn.isa.controller;

import com.ftn.isa.dto.request.ClinicReviewRequest;
import com.ftn.isa.dto.request.DoctorReviewRequest;
import com.ftn.isa.dto.response.ClinicReviewResponse;
import com.ftn.isa.dto.response.DoctorReviewResponse;
import com.ftn.isa.service.IReviewService;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/clinic/{id}/average-rating")
    public Double averageClinicReview(@PathVariable Long id) {
        return _reviewService.averageClinicRating(id);
    }

    @GetMapping("/doctor/{id}/average-rating")
    public Double averageDoctorReview(@PathVariable Long id) {
        return _reviewService.averageDoctorRating(id);
    }
}
