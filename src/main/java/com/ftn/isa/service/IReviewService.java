package com.ftn.isa.service;

import com.ftn.isa.dto.request.ClinicReviewRequest;
import com.ftn.isa.dto.request.DoctorReviewRequest;
import com.ftn.isa.dto.response.ClinicReviewResponse;
import com.ftn.isa.dto.response.DoctorReviewResponse;

public interface IReviewService {

    DoctorReviewResponse reviewingDoctor(DoctorReviewRequest request);

    ClinicReviewResponse reviewingClinic(ClinicReviewRequest request);

    DoctorReviewResponse averageDoctorRating(Long id);

    ClinicReviewResponse averageClinicRating(Long id);
}
