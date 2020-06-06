package com.ftn.isa.repository;

import com.ftn.isa.entity.ClinicReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClinicReviewRepository extends JpaRepository<ClinicReview, Long> {
}
