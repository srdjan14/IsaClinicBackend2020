package com.ftn.isa.repository;

import com.ftn.isa.entity.DoctorReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorReviewRepository extends JpaRepository<DoctorReview, Long> {

    DoctorReview findOneById(Long id);
}
