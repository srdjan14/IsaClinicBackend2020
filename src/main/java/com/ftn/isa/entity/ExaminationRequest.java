package com.ftn.isa.entity;

import com.ftn.isa.utils.enums.RequestStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExaminationRequest extends BaseEntity {

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "examinationType_id", referencedColumnName = "id")
    private ExaminationType examinationType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "operationRoom_id")
    private OperationRoom operationRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medicalStaff_id")
    private MedicalStaff medicalStaff;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clinic_id")
    private Clinic clinic;

    @Enumerated(EnumType.STRING)
    private RequestStatus status;

    private Date examinationDate;

    private LocalTime startAt;

    private LocalTime endAt;

    private float price;
}
