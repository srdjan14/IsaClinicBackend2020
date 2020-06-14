package com.ftn.isa.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UpdatePatientRequest {

    private String firstName;

    private String lastName;

    private String country;

    private String city;

    private String address;

    private String phone;

    private String ssn;
}
