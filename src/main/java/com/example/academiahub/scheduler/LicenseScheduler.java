package com.example.academiahub.scheduler;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.academiahub.model.CourseRequest;
import com.example.academiahub.repository.CourseRequestRepository;
import com.example.academiahub.service.LicenseAllocationService;
import com.example.academiahub.util.EmailService;

@Component
public class LicenseScheduler {
    private static final Logger logger = LoggerFactory.getLogger(LicenseScheduler.class);
    private final CourseRequestRepository courseRequestRepository;
    private final LicenseAllocationService licenseAllocationService;
    private final EmailService emailService;

    @Autowired
    public LicenseScheduler(CourseRequestRepository courseRequestRepository,
            LicenseAllocationService licenseAllocationService,
            EmailService emailService) {
        this.courseRequestRepository = courseRequestRepository;
        this.licenseAllocationService = licenseAllocationService;
        this.emailService = emailService;
    }

    @Scheduled(cron = "0 0 * * * *") // Every hour
    public void checkAndReallocateLicenses() {
        List<CourseRequest> expiredRequests = courseRequestRepository.findAll().stream()
                .filter(r -> "APPROVED".equalsIgnoreCase(r.getStatus()))
                .toList();
        for (CourseRequest request : expiredRequests) {
            logger.info("Expiring license for user: {} (course: {})", request.getUser().getName(),
                    request.getCourseName());
            // Notify user before expiring
            emailService.sendLicenseExpiryNotification(request.getUser(), request.getCourseName());
            request.setStatus("COMPLETED");
            courseRequestRepository.save(request);
        }
        // Only allocate license after all expired requests are processed
        if (!expiredRequests.isEmpty()) {
            licenseAllocationService.allocateLicenseToNextUser();
        }
        if (expiredRequests.isEmpty()) {
            logger.info("No licenses expired in this cycle.");
        }
    }
}
