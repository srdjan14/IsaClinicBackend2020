package com.ftn.isa.repository.filter.impl;

import com.ftn.isa.entity.ExaminationType;
import com.ftn.isa.entity.QExaminationType;
import com.ftn.isa.repository.filter.ExaminationTypeFilterableRepository;
import com.querydsl.jpa.impl.JPAQuery;

public class ExaminationTypeFilterableRepositoryImpl extends FilterableRepository<ExaminationType> implements ExaminationTypeFilterableRepository {

    ExaminationTypeFilterableRepositoryImpl() {
        super(ExaminationType.class);
    }

    @Override
    public JPAQuery<ExaminationType> getQuery() {
        return getJpaFactory().selectFrom(QExaminationType.examinationType);
    }
}
