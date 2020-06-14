package com.ftn.isa.repository.filter.impl;

import com.ftn.isa.entity.OperationRoom;
import com.ftn.isa.entity.QOperationRoom;
import com.ftn.isa.repository.filter.OperationRoomFilterableRepository;
import com.querydsl.jpa.impl.JPAQuery;

public class OperationRoomFilterableRepositoryImpl extends FilterableRepository<OperationRoom> implements OperationRoomFilterableRepository {

    OperationRoomFilterableRepositoryImpl() {
        super(OperationRoom.class);
    }

    @Override
    public JPAQuery<OperationRoom> getQuery() {
        return getJpaFactory().selectFrom(QOperationRoom.operationRoom);
    }
}
