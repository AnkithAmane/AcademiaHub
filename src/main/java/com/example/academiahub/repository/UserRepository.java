package com.example.academiahub.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.academiahub.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    // Custom query methods (if needed)
    Optional<User> findByEmail(String email);
}
