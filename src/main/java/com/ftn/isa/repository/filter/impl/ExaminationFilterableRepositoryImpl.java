package com.ftn.isa.repository.filter.impl;

import com.ftn.isa.entity.ExaminationRequest;
import com.ftn.isa.entity.QExaminationRequest;
import com.ftn.isa.repository.filter.ExaminationFilterableRepository;
import com.querydsl.jpa.impl.JPAQuery;

public class ExaminationFilterableRepositoryImpl extends FilterableRepository<ExaminationRequest> implements ExaminationFilterableRepository {

    ExaminationFilterableRepositoryImpl() {
        super(ExaminationRequest.class);
    }

    @Override
    public JPAQuery<ExaminationRequest> getQuery() {
        return getJpaFactory().selectFrom(QExaminationRequest.examinationRequest);
    }
}
