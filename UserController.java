package com.smartbus.controller;

import com.smartbus.dto.LoginRequest;
import com.smartbus.entity.User;
import com.smartbus.repository.UserRepository;
import com.smartbus.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // REGISTER
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", "Email already exists"
            ));
        }

        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("USER"); // default role
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);

        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "User registered successfully",
                "user", savedUser
        ));
    }

    // LOGIN
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());

        if (optionalUser.isEmpty() ||
            !passwordEncoder.matches(request.getPassword(), optionalUser.get().getPassword())) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", "Invalid email or password"
            ));
        }

        User user = optionalUser.get();
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());

        Map<String, Object> userDetails = new HashMap<>();
        userDetails.put("name", user.getName());
        userDetails.put("email", user.getEmail());
        userDetails.put("role", user.getRole());

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Login successful");
        response.put("user", userDetails);
        response.put("token", token);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/test")
    public String test() {
        return "API is working!";
    }
}



