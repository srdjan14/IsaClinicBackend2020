package com.ftn.isa.repository;

import com.ftn.isa.entity.Clinic;
import com.ftn.isa.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long>, QuerydslPredicateExecutor<Patient> {

    Patient findOneById(Long id);

    List<Patient> findAllByClinics(Clinic clinic);

    Patient findOneByUser_Email(String email);
}
