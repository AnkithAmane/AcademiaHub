package com.example.academiahub.util;

import org.springframework.stereotype.Service;

import com.example.academiahub.model.User;

@Service
public class EmailService {
    public void sendLicenseExpiryNotification(User user, String courseName) {
        // In a real implementation, send an email. For now, just print/log.
        System.out.println(
                "[EMAIL] License for course '" + courseName + "' is about to expire for user: " + user.getEmail());
    }
}
