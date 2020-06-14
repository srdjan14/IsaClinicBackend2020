package com.ftn.isa.dto.response;


import com.ftn.isa.utils.enums.AdminType;
import com.ftn.isa.utils.enums.DeletedStatus;
import com.ftn.isa.utils.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserResponse {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String country;

    private String city;

    private String address;

    private String phone;

    private String ssn;

    private UserType userType;

    private boolean setNewPassword;

    private ClinicResponse myClinic;

    private Long userId;

    private AdminType adminType;

    private DeletedStatus deletedStatus;
}
