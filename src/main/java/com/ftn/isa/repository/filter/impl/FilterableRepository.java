package com.ftn.isa.repository.filter.impl;

import com.ftn.isa.entity.QClinic;
import com.ftn.isa.repository.filter.IFilterableRepository;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public abstract class FilterableRepository<T> extends QuerydslRepositorySupport implements IFilterableRepository<T> {

    private JPAQueryFactory _jpaQuery;

    FilterableRepository(Class<?> domainClass) {
        super(domainClass);
    }

     JPAQueryFactory getJpaFactory() {
        if (_jpaQuery == null) {
            _jpaQuery = new JPAQueryFactory(getEntityManager());
        }
        return _jpaQuery;
    }
}
