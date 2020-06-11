package com.ftn.isa.repository;

import com.ftn.isa.entity.MedicalStaff;
import com.ftn.isa.entity.VacationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VacationRequestRepository extends JpaRepository<VacationRequest, Long>, QuerydslPredicateExecutor<VacationRequest> {

    VacationRequest findOneById(Long id);

    List<VacationRequest> findAllByMedicalStaff(MedicalStaff medicalStaff);

    List<VacationRequest> findAllByClinic(Long id);
}
