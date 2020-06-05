package com.ftn.isa.entity;

import com.ftn.isa.utils.enums.DeletedStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OperationRoom extends BaseEntity {

    private int number;

    @Column(unique = true)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clinic_id")
    private Clinic clinic;

    @Enumerated(EnumType.STRING)
    private DeletedStatus deletedStatus;
}
