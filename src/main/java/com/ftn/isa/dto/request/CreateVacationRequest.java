package com.ftn.isa.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateVacationRequest {

    private Date startAt;

    private Date endAt;

    private String description;

    private Long medicalStaffId;
}
