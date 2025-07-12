package com.example.academiahub.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.academiahub.model.CourseRequest;

public interface CourseRequestRepository extends JpaRepository<CourseRequest, Long> {

}
