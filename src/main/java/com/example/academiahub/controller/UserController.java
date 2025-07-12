package com.example.academiahub.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.academiahub.dto.UserDTO;
import com.example.academiahub.model.User;
import com.example.academiahub.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
@Validated
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@Valid @RequestBody UserDTO dto) {
        try {
            // Log the incoming request payload
            System.out.println("Incoming registration payload: " + dto);

            // Validate the payload
            if (dto.getName() == null || dto.getEmail() == null || dto.getRole() == null
                    || dto.getDepartment() == null) {
                System.err.println("Validation failed: Missing required fields");
                throw new IllegalArgumentException("Missing required fields in the request payload.");
            }

            // Save user details in the database
            userService.registerUser(dto);

            // Log successful registration
            System.out.println("User registered successfully: " + dto.getEmail());

            // After successful registration, redirect to home page
            return ResponseEntity.status(HttpStatus.SEE_OTHER)
                    .header("Access-Control-Allow-Origin", "*")
                    .header("Location", "/api/users/home")
                    .build();
        } catch (IllegalArgumentException e) {
            // Log validation errors
            System.err.println("Validation error: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (Exception e) {
            // Log unexpected errors
            System.err.println("Unexpected error during registration: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error registering user", e);
        }
    }

    @GetMapping("/{email}")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
        // Exclude static file paths
        if (email.endsWith(".html")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        System.out.println("Incoming email parameter: " + email);
        User user = userService.getUserByEmail(email);
        UserDTO userDTO = new UserDTO(user.getId(), user.getName(), user.getEmail()); // Convert User to UserDTO
        return ResponseEntity.ok(userDTO);
    }

    @PutMapping("/{email}")
    public User updateUser(@PathVariable String email, @RequestBody UserDTO dto) {
        return userService.updateUser(email, dto);
    }

    @GetMapping("/data")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<UserDTO> userDTOs = users.stream()
                .map(user -> new UserDTO(user.getId(), user.getName(), user.getEmail())) // Convert User to UserDTO
                .toList();
        return ResponseEntity.ok(userDTOs);
    }

    @GetMapping("/profile")
    public ResponseEntity<User> getUserProfile(HttpServletRequest request) {
        String email = request.getUserPrincipal().getName();
        try {
            User user = userService.getUserProfile(email);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found", e);
        }
    }
}
