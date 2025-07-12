package com.example.academiahub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.academiahub.model.CourseRequest;
import com.example.academiahub.model.User;

public interface RequestRepository extends JpaRepository<CourseRequest, Long> {
    List<CourseRequest> findByUser(User user);
}
