package com.ftn.isa.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateExaminationRequest {

    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date examinationDate;

    private Long doctorId;

    private Long patientId;

    private Long examinationTypeId;

    private Long clinicId;

    private String startAt;
}
