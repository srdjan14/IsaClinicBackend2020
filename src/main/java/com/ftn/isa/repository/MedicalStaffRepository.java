package com.ftn.isa.repository;

import com.ftn.isa.entity.Clinic;
import com.ftn.isa.entity.MedicalStaff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicalStaffRepository extends JpaRepository<MedicalStaff, Long>, QuerydslPredicateExecutor<MedicalStaff> {

    MedicalStaff findOneById(Long id);

    List<MedicalStaff> findAllByClinic(Clinic clinic);

}
