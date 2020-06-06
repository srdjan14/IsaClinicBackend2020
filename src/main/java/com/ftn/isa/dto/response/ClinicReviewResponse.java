package com.ftn.isa.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClinicReviewResponse {

    private Long id;

    private Long clinicId;

    private Long patientId;

    private int review;

    private String description;

}
