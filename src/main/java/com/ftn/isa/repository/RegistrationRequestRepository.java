package com.ftn.isa.repository;

import com.ftn.isa.entity.RegistrationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RegistrationRequestRepository extends JpaRepository<RegistrationRequest, UUID> {

    RegistrationRequest findOneById(Long id);
}
