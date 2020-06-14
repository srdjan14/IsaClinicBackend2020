package com.ftn.isa.repository.filter.impl;

import com.ftn.isa.entity.Clinic;
import com.ftn.isa.entity.QClinic;
import com.ftn.isa.repository.filter.ClinicFilterableRepository;
import com.querydsl.jpa.impl.JPAQuery;


public class ClinicFilterableRepositoryImpl extends FilterableRepository<Clinic> implements ClinicFilterableRepository {
    public ClinicFilterableRepositoryImpl() {
        super(Clinic.class);
    }

    @Override
    public JPAQuery<Clinic> getQuery() {
        return getJpaFactory().selectFrom(QClinic.clinic);
    }
}
