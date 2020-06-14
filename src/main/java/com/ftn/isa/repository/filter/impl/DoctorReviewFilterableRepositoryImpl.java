package com.ftn.isa.repository.filter.impl;

import com.ftn.isa.entity.DoctorReview;
import com.ftn.isa.entity.QDoctorReview;
import com.ftn.isa.repository.filter.DoctorReviewFilterableRepository;
import com.querydsl.jpa.impl.JPAQuery;

public class DoctorReviewFilterableRepositoryImpl extends FilterableRepository<DoctorReview> implements DoctorReviewFilterableRepository {

    DoctorReviewFilterableRepositoryImpl() {
        super(DoctorReview.class);
    }

    @Override
    public JPAQuery<DoctorReview> getQuery() {
        return getJpaFactory().selectFrom(QDoctorReview.doctorReview);
    }
}
