package com.example.academiahub.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.academiahub.model.CourseRequest;
import com.example.academiahub.model.QueueEntry;
import com.example.academiahub.repository.CourseRequestRepository;

@Service
public class LicenseAllocationService {
    private final QueueService queueService;
    private final CourseRequestRepository courseRequestRepository;

    @Autowired
    public LicenseAllocationService(QueueService queueService, CourseRequestRepository courseRequestRepository) {
        this.queueService = queueService;
        this.courseRequestRepository = courseRequestRepository;
    }

    public void allocateLicenseToNextUser() {
        // Get the next queued request
        Optional<QueueEntry> nextEntry = queueService.getNextInQueue();
        if (nextEntry.isPresent()) {
            CourseRequest nextRequest = nextEntry.get().getCourseRequest();
            nextRequest.setStatus("APPROVED");
            courseRequestRepository.save(nextRequest);
            queueService.removeFromQueue(nextEntry.get());
        }
    }
}
