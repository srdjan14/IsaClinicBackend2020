package com.ftn.isa.repository.filter.impl;

import com.ftn.isa.entity.QRegistrationRequest;
import com.ftn.isa.entity.RegistrationRequest;
import com.ftn.isa.repository.filter.RegistrationFilterableRepository;
import com.querydsl.jpa.impl.JPAQuery;

public class RegistrationFilterableRepositoryImpl extends FilterableRepository<RegistrationRequest> implements RegistrationFilterableRepository {

    RegistrationFilterableRepositoryImpl() {
        super(RegistrationRequest.class);
    }

    @Override
    public JPAQuery<RegistrationRequest> getQuery() {
        return getJpaFactory().selectFrom(QRegistrationRequest.registrationRequest);
    }
}
