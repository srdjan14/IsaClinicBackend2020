package com.ftn.isa.repository.filter.impl;

import com.ftn.isa.entity.QVacationRequest;
import com.ftn.isa.entity.VacationRequest;
import com.ftn.isa.repository.filter.VacationFilterableRepository;
import com.querydsl.jpa.impl.JPAQuery;

public class VacationFilterableRepositoryImpl extends FilterableRepository<VacationRequest> implements VacationFilterableRepository {

    VacationFilterableRepositoryImpl() {
        super(VacationRequest.class);
    }

    @Override
    public JPAQuery<VacationRequest> getQuery() {
        return getJpaFactory().selectFrom(QVacationRequest.vacationRequest);
    }
}
