package com.example.academiahub.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.academiahub.dto.CourseRequestDTO;
import com.example.academiahub.model.CourseRequest;
import com.example.academiahub.model.User;
import com.example.academiahub.repository.CourseRequestRepository;
import com.example.academiahub.repository.UserRepository;

@Service
public class CourseRequestService {
    private final CourseRequestRepository courseRequestRepository;
    private final UserRepository userRepository;
    private final QueueService queueService;
    private static final int MAX_LICENSES = 5;

    @Autowired
    public CourseRequestService(CourseRequestRepository courseRequestRepository, UserRepository userRepository,
            QueueService queueService) {
        this.courseRequestRepository = courseRequestRepository;
        this.userRepository = userRepository;
        this.queueService = queueService;
    }

    public CourseRequest submitRequest(CourseRequestDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + dto.getUserId()));
        CourseRequest request = new CourseRequest(
                dto.getCourseName(),
                dto.getJustification(),
                "PENDING", // default status
                LocalDateTime.now(),
                user);
        // License availability logic: max 5 active licenses per course
        long activeLicenses = courseRequestRepository.findAll().stream()
                .filter(r -> r.getCourseName().equalsIgnoreCase(dto.getCourseName())
                        && "APPROVED".equalsIgnoreCase(r.getStatus()))
                .count();
        if (activeLicenses >= MAX_LICENSES) {
            // Add to queue
            queueService.addToQueue(request);
            request.setStatus("QUEUED");
        } else {
            // Approve directly
            request.setStatus("APPROVED");
        }
        return courseRequestRepository.save(request);
    }

    public List<CourseRequest> getAllRequests() {
        return courseRequestRepository.findAll();
    }

    public List<CourseRequest> getWaitingRequestsForCourse(String courseName) {
        return courseRequestRepository.findAll().stream()
                .filter(r -> r.getCourseName().equalsIgnoreCase(courseName)
                        && "PENDING".equalsIgnoreCase(r.getStatus()))
                .toList();
    }
}
