package com.ftn.isa.dto.response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VacationRequestResponse {

    private Long id;

    private Date startAt;

    private Date endAt;

    private String description;

    private Long medicalStaffId;

    private boolean confirmed;

}

