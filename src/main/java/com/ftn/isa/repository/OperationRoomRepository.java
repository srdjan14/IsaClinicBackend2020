package com.ftn.isa.repository;

import com.ftn.isa.entity.OperationRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface OperationRoomRepository extends JpaRepository<OperationRoom, Long>, QuerydslPredicateExecutor<OperationRoom> {

    List<OperationRoom> findAllByClinic_Id(Long clinicId);

    OperationRoom findOneById(Long id);
}
