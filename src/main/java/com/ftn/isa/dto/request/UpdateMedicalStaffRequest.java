package com.ftn.isa.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UpdateMedicalStaffRequest {

    private String firstName;

    private String lastName;

    private String country;

    private String city;

    private String address;

    private String phone;

    private String ssn;

    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime startAt;

    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime endAt;

}
