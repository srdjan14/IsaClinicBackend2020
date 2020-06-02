package com.ftn.isa.entity;

import com.ftn.isa.utils.enums.RequestStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationRequest extends BaseEntity {

    private String firstName;

    private String lastName;

    @Column(unique = true)
    private String email;

    private String country;

    private String city;

    private String address;

    private String phone;

    @Column(nullable = false)
    private String password;

    private String ssn;

    @Enumerated(EnumType.STRING)
    private RequestStatus status;
}
