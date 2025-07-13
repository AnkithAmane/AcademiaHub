package com.example.academiahub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.academiahub.dto.CourseRequestSubmissionDTO;
import com.example.academiahub.dto.StatusUpdateDTO;
import com.example.academiahub.model.CourseRequest;
import com.example.academiahub.service.CourseRequestService;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class CourseRequestController {
    @PutMapping("/api/course-requests/{id}/approve")
    public ResponseEntity<?> approveCourseRequest(@PathVariable Long id) {
        try {
            CourseRequest req = courseRequestService.getRequestById(id);
            if (req == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Request not found");
            req.setStatus("Approved");
            courseRequestService.saveRequest(req);
            return ResponseEntity.ok("Request approved");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/api/course-requests/{id}/reject")
    public ResponseEntity<?> rejectCourseRequest(@PathVariable Long id) {
        try {
            CourseRequest req = courseRequestService.getRequestById(id);
            if (req == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Request not found");
            req.setStatus("Rejected");
            courseRequestService.saveRequest(req);
            return ResponseEntity.ok("Request rejected");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
    private final CourseRequestService courseRequestService;

    @Autowired
    public CourseRequestController(CourseRequestService courseRequestService) {
        this.courseRequestService = courseRequestService;
    }

    @PostMapping("/course-requests/update-status")
    public ResponseEntity<?> updateRequestStatus(@RequestBody StatusUpdateDTO dto) {
        try {
            boolean updated = courseRequestService.updateStatusByEmail(dto.getEmail(), dto.getStatus());
            if (updated) {
                return ResponseEntity.ok().body("Status updated successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Request not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating status: " + e.getMessage());
        }
    }

    @GetMapping("/request-access")
    public ResponseEntity<Resource> getRequestAccessForm() {
        try {
            Resource resource = new ClassPathResource("static/api/users/request-access.html");
            return ResponseEntity.ok()
                    .contentType(MediaType.TEXT_HTML)
                    .body(resource);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/request-access")
    public ResponseEntity<?> submitRequest(@RequestBody CourseRequestSubmissionDTO dto) {
        try {
            CourseRequest request = courseRequestService.submitRequest(dto);
            return ResponseEntity.ok(request);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error submitting request: " + e.getMessage());
        }
    }

    @GetMapping("/requests")
    public ResponseEntity<List<CourseRequest>> getUserRequests() {
        try {
            List<CourseRequest> requests = courseRequestService.getRequestsForCurrentUser();
            return ResponseEntity.ok(requests);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
