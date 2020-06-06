package com.ftn.isa.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ftn.isa.utils.enums.RequestStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExaminationRequestResponse {

    private Long id;

    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date examinationDate;

    private RequestStatus requestStatus;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime startAt;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime endAt;

    private String examinationTypeName;

    private Long examinationTypeId;

}
