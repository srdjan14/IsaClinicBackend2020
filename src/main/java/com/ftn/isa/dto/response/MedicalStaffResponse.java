package com.ftn.isa.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ftn.isa.utils.enums.MedicalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicalStaffResponse {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String country;

    private String city;

    private String address;

    private String phone;

    private String ssn;

    private MedicalType medicalType;

    private Long clinicId;

    private String speciality;

    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime startAt;

    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime endAt;

}
