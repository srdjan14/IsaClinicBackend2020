package com.ftn.isa.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchMedicalStaffRequest {

    private String firstName;

    private String lastName;

    private String examinationType;
}
