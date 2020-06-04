package com.ftn.isa.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorsSpecialityResponse {

    private String speciality;

    private String price;

    private Long id;
}
