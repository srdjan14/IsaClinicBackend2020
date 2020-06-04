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

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "operationRoom_id", referencedColumnName = "id")
    private OperationRoom operationRoom;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "medicalStaff_id", referencedColumnName = "id")
    private MedicalStaff medicalStaff;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    private Patient patient;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clinic_id", referencedColumnName = "id")
    private Clinic clinic;

    @Enumerated(EnumType.STRING)
    private RequestStatus status;

    private Date examinationDate;

    private LocalTime startAt;

    private LocalTime endAt;
}
