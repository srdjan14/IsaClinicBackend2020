package com.ftn.isa.service;

import com.ftn.isa.dto.request.ClinicReviewRequest;
import com.ftn.isa.dto.request.DoctorReviewRequest;
import com.ftn.isa.dto.response.ClinicReviewResponse;
import com.ftn.isa.dto.response.DoctorReviewResponse;

public interface IReviewService {

    DoctorReviewResponse reviewingDoctor(DoctorReviewRequest request) throws Exception;

    ClinicReviewResponse reviewingClinic(ClinicReviewRequest request) throws Exception;

    Double averageDoctorRating(Long id);

    Double averageClinicRating(Long id);
}
