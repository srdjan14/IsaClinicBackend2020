package com.ftn.isa.repository;

import com.ftn.isa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findOneById(Long id);

    User findOneByEmail(String email);
}
