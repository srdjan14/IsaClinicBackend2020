package com.ftn.isa.repository;

import com.ftn.isa.entity.Clinic;
import com.ftn.isa.repository.filter.ClinicFilterableRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ClinicRepository extends JpaRepository<Clinic, Long>, QuerydslPredicateExecutor<Clinic>, ClinicFilterableRepository {

    Clinic findOneById(Long id);

}
