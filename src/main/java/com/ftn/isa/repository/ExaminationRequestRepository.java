package com.ftn.isa.repository;

import com.ftn.isa.entity.Clinic;
import com.ftn.isa.entity.ExaminationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExaminationRequestRepository extends JpaRepository<ExaminationRequest, Long> {

    ExaminationRequest findOneById(Long id);

    List<ExaminationRequest> findAllByPatient_Id(Long id);
}
