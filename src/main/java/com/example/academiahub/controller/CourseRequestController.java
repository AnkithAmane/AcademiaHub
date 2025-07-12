package com.example.academiahub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.academiahub.dto.CourseRequestDTO;
import com.example.academiahub.model.CourseRequest;
import com.example.academiahub.service.CourseRequestService;

@RestController
@RequestMapping("/api/requests")
public class CourseRequestController {
    private final CourseRequestService courseRequestService;

    @Autowired
    public CourseRequestController(CourseRequestService courseRequestService) {
        this.courseRequestService = courseRequestService;
    }

    @PostMapping
    public CourseRequest submitRequest(@RequestBody CourseRequestDTO dto) {
        return courseRequestService.submitRequest(dto);
    }

    @GetMapping
    public List<CourseRequest> getAllRequests() {
        return courseRequestService.getAllRequests();
    }
}
