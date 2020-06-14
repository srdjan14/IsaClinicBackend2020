package com.ftn.isa.repository.filter.impl;

import com.ftn.isa.entity.ClinicReview;
import com.ftn.isa.entity.QClinicReview;
import com.ftn.isa.repository.filter.ClinicReviewFilterableRepository;
import com.querydsl.jpa.impl.JPAQuery;

public class ClinicReviewFilterableRepositoryImpl extends FilterableRepository<ClinicReview> implements ClinicReviewFilterableRepository {

    ClinicReviewFilterableRepositoryImpl() {
        super(ClinicReview.class);
    }

    @Override
    public JPAQuery<ClinicReview> getQuery() {
        return getJpaFactory().selectFrom(QClinicReview.clinicReview);
    }
}
