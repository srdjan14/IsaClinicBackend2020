package com.ftn.isa.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchExaminationRequest {

    private String examinationTypeName;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date examinationDate;

    private float price;
}
