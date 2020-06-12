package com.ftn.isa.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClinicResponse {

    private Long id;

    private String name;

    private String address;

    private String description;

    private float x;

    private float y;

}
