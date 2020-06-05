package com.ftn.isa.repository;

import com.ftn.isa.entity.OperationRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OperationRoomRepository extends JpaRepository<OperationRoom, Long> {

    List<OperationRoom> findAllByClinic_Id(Long clinicId);

    OperationRoom findOneById(Long id);
}
