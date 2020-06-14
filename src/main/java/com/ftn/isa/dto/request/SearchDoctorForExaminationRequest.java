package com.ftn.isa.dto.request;

import com.ftn.isa.entity.ExaminationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchDoctorForExaminationRequest {

    private Date examinationDate;

    private ExaminationType examinationType;
}
