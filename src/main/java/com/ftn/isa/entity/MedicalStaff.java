package com.ftn.isa.entity;

import com.ftn.isa.utils.enums.MedicalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MedicalStaff extends BaseEntity{

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Enumerated(EnumType.STRING)
    private MedicalType medicalType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clinic_id")
    private Clinic clinic;

    @ManyToMany(cascade = CascadeType.DETACH)
    @JoinTable(name = "medical_patient",
            joinColumns = @JoinColumn(name = "medical_id"),
            inverseJoinColumns = @JoinColumn(name = "patient_id")
    )
    private Set<Patient> patients;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "examinationType_id")
    private ExaminationType examinationType;

    private LocalTime startWorkAt;

    private LocalTime endWorkAt;

    @OneToMany(mappedBy = "medicalStaff", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DoctorReview> grades = new ArrayList<>();
}