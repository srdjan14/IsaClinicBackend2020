package com.ftn.isa.repository;

import com.ftn.isa.entity.MedicalStaff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MedicalStaffRepository extends JpaRepository<MedicalStaff, UUID> {

    MedicalStaff findOneById(UUID id);
}
