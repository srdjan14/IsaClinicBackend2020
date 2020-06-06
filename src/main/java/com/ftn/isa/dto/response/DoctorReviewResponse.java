package com.ftn.isa.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorReviewResponse {

    private Long id;

    private Long medicalStaffId;

    private Long patientId;

    private int review;

    private String description;
}
