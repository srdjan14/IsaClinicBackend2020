package com.ftn.isa.repository;

import com.ftn.isa.entity.DoctorsSpeciality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorsSpecialityRepository extends JpaRepository<DoctorsSpeciality, Long> {

    DoctorsSpeciality findOneById(Long id);
}
