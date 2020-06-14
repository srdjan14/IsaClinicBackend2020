package com.ftn.isa.repository.filter;

import com.querydsl.jpa.impl.JPAQuery;

public interface IFilterableRepository<T> {

    JPAQuery<T> getQuery();
}
