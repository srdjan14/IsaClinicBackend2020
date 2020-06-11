package com.ftn.isa.repository;

import com.ftn.isa.entity.ExaminationRequest;
import com.ftn.isa.repository.filter.ExaminationFilterableRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExaminationRequestRepository extends JpaRepository<ExaminationRequest, Long>, QuerydslPredicateExecutor<ExaminationRequest>, ExaminationFilterableRepository {

    ExaminationRequest findOneById(Long id);

    List<ExaminationRequest> findAllByPatient_Id(Long id);

    List<ExaminationRequest> findAllByMedicalStaff_Id(Long id);
}
