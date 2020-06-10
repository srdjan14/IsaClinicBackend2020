package com.ftn.isa.repository;

import com.ftn.isa.entity.ExaminationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ExaminationTypeRepository extends JpaRepository<ExaminationType, Long>, QuerydslPredicateExecutor<ExaminationType> {

    ExaminationType findOneById(Long id);
}
