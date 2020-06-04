package com.ftn.isa.entity;

import com.ftn.isa.utils.enums.DeletedStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExaminationType extends BaseEntity {

    @OneToMany(mappedBy = "examinationType", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MedicalStaff> medicalStaff = new ArrayList<>();

    private String name;

    private float price;

    @Enumerated(EnumType.STRING)
    private DeletedStatus deletedStatus;
}