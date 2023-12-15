package com.practice.financialtracker.repository;

import com.practice.financialtracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUsersByUserName(String username);

}

