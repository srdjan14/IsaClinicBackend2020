package com.ftn.isa.repository;

import com.ftn.isa.entity.Clinic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ClinicRepository extends JpaRepository<Clinic, Long> {

    Clinic findOneById(Long id);

}