package com.ftn.isa.repository;

import com.ftn.isa.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long>, QuerydslPredicateExecutor<Admin> {

    Admin findOneById(Long id);

}
