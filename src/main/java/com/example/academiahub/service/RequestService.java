
package com.example.academiahub.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.academiahub.model.CourseRequest;
import com.example.academiahub.repository.RequestRepository;
import com.example.academiahub.repository.UserRepository;

@Service
public class RequestService {

    public List<CourseRequest> findByEmail(String email) {
        var userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            return requestRepository.findByUser(userOpt.get());
        }
        return List.of();
    }

    private final RequestRepository requestRepository;
    private final UserRepository userRepository;

    public RequestService(RequestRepository requestRepository, UserRepository userRepository) {
        this.requestRepository = requestRepository;
        this.userRepository = userRepository;
    }

    public CourseRequest saveRequest(CourseRequest request) {
        // Force robust user association and log
        com.example.academiahub.model.User user = null;
        if (request.getUser() != null) {
            user = request.getUser();
        } else if (request.getEmail() != null) {
            user = userRepository.findByEmail(request.getEmail()).orElse(null);
        } else if (request.getId() != null) {
            user = userRepository.findById(request.getId()).orElse(null);
        }
        if (user == null) {
            System.err.println("[ERROR] No user found for request: " + request.getEmail() + ", id: " + request.getId());
        } else {
            request.setUser(user);
            System.out.println("[INFO] Linked request to user: " + user.getId() + " (" + user.getEmail() + ")");
        }
        // Ensure all fields are set (if missing)
        if (request.getCourseDuration() == null && request.getCourseDuration() != null) {
            request.setCourseDuration(request.getCourseDuration());
        }
        if (request.getCourseProvider() == null && request.getCourseProvider() != null) {
            request.setCourseProvider(request.getCourseProvider());
        }
        if (request.getCourseDesc() == null && request.getCourseDesc() != null) {
            request.setCourseDesc(request.getCourseDesc());
        }
        return requestRepository.save(request);
    }

    public List<CourseRequest> findByUserIdAndEmail(Long userId, String email) {
        var userOpt = userRepository.findById(userId);
        if (userOpt.isPresent() && userOpt.get().getEmail().equalsIgnoreCase(email)) {
            return requestRepository.findByUser(userOpt.get());
        }
        return List.of();
    }
}
