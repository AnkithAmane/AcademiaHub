package com.example.academiahub.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.academiahub.model.CourseRequest;
import com.example.academiahub.service.RequestService;

@RestController
@RequestMapping("/api/course-requests")
@CrossOrigin(origins = "*")
public class RequestController {

    private static final Logger logger = LoggerFactory.getLogger(RequestController.class);

    private final RequestService requestService;

    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @PostMapping
    public CourseRequest submitRequest(@RequestBody CourseRequest request) {
        logger.info("Received request: {}", request);

        return requestService.saveRequest(request);
    }

    @GetMapping("/user/{userId}/{email}")
    public List<CourseRequest> getRequestsByUserIdAndEmail(@PathVariable Long userId, @PathVariable String email) {
        return requestService.findByUserIdAndEmail(userId, email);
    }

    @GetMapping("/by-email/{email}")
    public List<CourseRequest> getRequestsByEmail(@PathVariable String email) {
        return requestService.findByEmail(email);
    }
}
