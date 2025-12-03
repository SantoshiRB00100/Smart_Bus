package com.smartbus.service;

import com.smartbus.dto.GoogleAuthRequest;
import com.smartbus.dto.LoginRequest;
import com.smartbus.dto.SignupRequest;
import com.smartbus.entity.User;
import com.smartbus.repository.UserRepository;
import com.smartbus.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil; // <-- ADDED

    // Constructor updated to include jwtUtil
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // ---------------- SIGNUP ----------------
    public Map<String, Object> register(SignupRequest request) {
        Map<String, Object> response = new HashMap<>();

        if (userRepository.existsByEmail(request.getEmail())) {
            response.put("success", false);
            response.put("message", "User already exists!");
            return response;
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setName(request.getName());
        user.setProvider("LOCAL");

        if (request.getRole() == null || request.getRole().isEmpty()) {
            user.setRole("USER");
        } else {
            user.setRole(request.getRole());
        }

        userRepository.save(user);

        response.put("success", true);
        response.put("message", "User registered successfully!");
        return response;
    }

    // ---------------- LOGIN ----------------
    public Map<String, Object> loginUser(LoginRequest request) {
        Map<String, Object> response = new HashMap<>();

        User user = userRepository.findByEmail(request.getEmail()).orElse(null);

        if (user == null) {
            response.put("success", false);
            response.put("message", "User does not exist!");
            return response;
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            response.put("success", false);
            response.put("message", "Invalid password!");
            return response;
        }

        // Generate JWT Token
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());

        response.put("success", true);
        response.put("message", "Login successful!");
        response.put("token", token);

        Map<String, Object> userData = new HashMap<>();
        userData.put("email", user.getEmail());
        userData.put("name", user.getName());
        userData.put("role", user.getRole());

        response.put("user", userData);

        return response;
    }

    // ---------------- GOOGLE LOGIN ----------------
    public Map<String, Object> googleLogin(GoogleAuthRequest request) {
        Map<String, Object> response = new HashMap<>();

        User user = userRepository.findByEmail(request.getEmail()).orElse(null);

        if (user == null) {
            User newUser = new User();
            newUser.setEmail(request.getEmail());
            newUser.setName(request.getName());
            newUser.setProvider("GOOGLE");
            newUser.setRole("USER");
            userRepository.save(newUser);
            user = newUser;
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());

        response.put("success", true);
        response.put("message", "Google login successful!");
        response.put("token", token);

        Map<String, Object> userDetails = new HashMap<>();
        userDetails.put("name", user.getName());
        userDetails.put("email", user.getEmail());
        userDetails.put("role", user.getRole());
        response.put("user", userDetails);

        return response;
    }
}



