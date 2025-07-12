package com.example.academiahub.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserFormController {

    @GetMapping("/")
    public ResponseEntity<Resource> root() {
        // Serve signup/login page directly
        try {
            Resource resource = new ClassPathResource("static/index.html");
            return ResponseEntity.ok()
                    .contentType(MediaType.TEXT_HTML)
                    .body(resource);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/signup")
    public ResponseEntity<Resource> signup() {
        // Serve signup page (same as root)
        try {
            Resource resource = new ClassPathResource("static/index.html");
            return ResponseEntity.ok()
                    .contentType(MediaType.TEXT_HTML)
                    .body(resource);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/login")
    public ResponseEntity<Resource> login() {
        // Serve login page (same as root)
        try {
            Resource resource = new ClassPathResource("static/index.html");
            return ResponseEntity.ok()
                    .contentType(MediaType.TEXT_HTML)
                    .body(resource);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/api/users/index")
    public ResponseEntity<Resource> registration() {
        // Serve registration page
        try {
            Resource resource = new ClassPathResource("static/api/users/index.html");
            return ResponseEntity.ok()
                    .contentType(MediaType.TEXT_HTML)
                    .body(resource);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/api/users/index")
    public String handleRegistration() {
        // After registration, redirect to home
        return "redirect:/api/users/home";
    }

    @GetMapping("/api/users/home")
    public ResponseEntity<Resource> home() {
        // Serve home page
        try {
            Resource resource = new ClassPathResource("static/api/users/home.html");
            return ResponseEntity.ok()
                    .contentType(MediaType.TEXT_HTML)
                    .body(resource);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/api/users/request-access")
    public ResponseEntity<Resource> requestAccess() {
        // Serve request access page
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

    @GetMapping({ "/api/users/all-requests", "/api/users/all-requests.html" })
    public ResponseEntity<Resource> allRequestsPage() {
        try {
            Resource resource = new ClassPathResource("static/api/users/all-requests.html");
            return ResponseEntity.ok()
                    .contentType(MediaType.TEXT_HTML)
                    .body(resource);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }
}
