package com.ftn.isa.repository;

import com.ftn.isa.entity.RegistrationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrationRequestRepository extends JpaRepository<RegistrationRequest, Long> {

    RegistrationRequest findOneById(Long id);
}
