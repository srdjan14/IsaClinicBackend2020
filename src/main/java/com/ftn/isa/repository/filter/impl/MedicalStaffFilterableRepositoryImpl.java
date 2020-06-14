package com.ftn.isa.repository.filter.impl;

import com.ftn.isa.entity.MedicalStaff;
import com.ftn.isa.entity.QMedicalStaff;
import com.ftn.isa.repository.filter.MedicalStaffFilterableRepository;
import com.querydsl.jpa.impl.JPAQuery;

public class MedicalStaffFilterableRepositoryImpl extends FilterableRepository<MedicalStaff> implements MedicalStaffFilterableRepository {

    MedicalStaffFilterableRepositoryImpl() {
        super(MedicalStaff.class);
    }

    @Override
    public JPAQuery<MedicalStaff> getQuery() {
        return getJpaFactory().selectFrom(QMedicalStaff.medicalStaff);
    }
}
