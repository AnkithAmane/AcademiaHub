package com.example.academiahub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CourseRequestPageController {
    @GetMapping("/api/course-requests")
    public String courseRequestsPage() {
        return "forward:/api/users/requests.html";
    }
}
