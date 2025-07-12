package com.example.academiahub.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.academiahub.dto.UserDTO;
import com.example.academiahub.model.User;
import com.example.academiahub.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(UserDTO dto) {
        try {
            // Check if user already exists
            User existingUser = userRepository.findByEmail(dto.getEmail()).orElse(null);
            if (existingUser != null) {
                throw new IllegalArgumentException("User with email " + dto.getEmail() + " already exists.");
            }

            // Create User record
            User user = new User(dto.getName(), dto.getEmail(), dto.getRole(), dto.getDepartment());
            return userRepository.save(user);
        } catch (Exception e) {
            System.err.println("Error during user registration: " + e.getMessage());
            throw e;
        }
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserByEmail(String email) {
        System.out.println("Searching for user with email: " + email);
        return userRepository.findByEmail(email).orElse(null);
    }

    public User updateUser(String email, UserDTO dto) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        user.setName(dto.getName());
        user.setRole(dto.getRole());
        user.setDepartment(dto.getDepartment());
        return userRepository.save(user);
    }

    public User getUserProfile(String email) {
        // Fetch user by email
        return userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
    }
}
