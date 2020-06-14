package com.ftn.isa.dto.request;

import com.ftn.isa.utils.enums.AdminType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateAdminRequest {

    private String firstName;

    private String lastName;

    private String email;

    private String country;

    private String city;

    private String address;

    private String phone;

    private String ssn;

    private String password;

    private String rePassword;

    private AdminType adminType;

    private Long clinicId;
}
