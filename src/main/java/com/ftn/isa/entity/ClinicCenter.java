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
public class ClinicCenter extends BaseEntity{

    @OneToMany(mappedBy = "clinicCenter", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Clinic> clinics = new ArrayList<>();
}
