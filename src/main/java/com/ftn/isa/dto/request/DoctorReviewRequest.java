package com.ftn.isa.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorReviewRequest {

    private Long medicalStaffId;

    private Long patientId;

    private Double review;

    private String description;
}
