package com.ftn.isa.repository;

import com.ftn.isa.entity.DoctorReview;
import com.ftn.isa.repository.filter.DoctorReviewFilterableRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorReviewRepository extends JpaRepository<DoctorReview, Long>, QuerydslPredicateExecutor<DoctorReview>, DoctorReviewFilterableRepository {

    DoctorReview findOneById(Long id);
}
