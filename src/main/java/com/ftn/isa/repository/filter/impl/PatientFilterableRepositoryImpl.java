package com.ftn.isa.repository.filter.impl;

import com.ftn.isa.entity.Patient;
import com.ftn.isa.entity.QPatient;
import com.ftn.isa.repository.filter.PatientFilterableRepository;
import com.querydsl.jpa.impl.JPAQuery;

public class PatientFilterableRepositoryImpl extends FilterableRepository<Patient> implements PatientFilterableRepository {

    PatientFilterableRepositoryImpl() {
        super(Patient.class);
    }

    @Override
    public JPAQuery<Patient> getQuery() {
        return getJpaFactory().selectFrom(QPatient.patient);
    }
}
