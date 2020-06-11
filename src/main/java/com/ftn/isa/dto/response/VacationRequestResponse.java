package com.ftn.isa.dto.response;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VacationRequestResponse {

    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startAt;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endAt;

    private String description;

    private Long medicalStaffId;

    private boolean confirmed;

}

