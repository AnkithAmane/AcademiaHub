package com.example.academiahub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.academiahub.model.UserAuth;
import com.example.academiahub.repository.UserAuthRepository;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class UserAuthController {
    @Autowired
    private UserAuthRepository userAuthRepository;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserAuth userAuth) {
        if (userAuthRepository.findByEmail(userAuth.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already registered.");
        }
        userAuthRepository.save(userAuth);
        return ResponseEntity.ok("Signup successful.");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserAuth loginRequest) {
        return userAuthRepository.findByEmail(loginRequest.getEmail())
                .filter(u -> u.getPassword().equals(loginRequest.getPassword()))
                .map(u -> ResponseEntity.ok(u.getRole()))
                .orElse(ResponseEntity.status(401).body("Invalid credentials."));
    }
}
