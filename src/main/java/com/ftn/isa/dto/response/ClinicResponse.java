package com.ftn.isa.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClinicResponse {

    private UUID id;

    private String name;

    private String address;

    private String description;
}
