package com.aek56.microservice.auth.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.aek56.microservice.auth.model.security.User;


public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
