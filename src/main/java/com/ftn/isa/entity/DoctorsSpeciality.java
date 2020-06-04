package com.ftn.isa.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DoctorsSpeciality extends BaseEntity {

    @OneToMany(mappedBy = "doctorsSpeciality", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MedicalStaff> medicalStaff = new ArrayList<>();

    private String speciality;

    private String price;
}
