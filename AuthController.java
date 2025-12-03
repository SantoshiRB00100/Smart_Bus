package com.smartbus.controller;

import com.smartbus.dto.LoginRequest;
import com.smartbus.dto.SignupRequest;
import com.smartbus.entity.User;
import com.smartbus.repository.UserRepository;
import com.smartbus.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/signup")
    public Map<String, Object> signup(@RequestBody SignupRequest request) {
        Map<String, Object> response = new HashMap<>();

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            response.put("success", false);
            response.put("message", "Email already exists!");
            return response;
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole().substring(0,1).toUpperCase() + request.getRole().substring(1).toLowerCase());


        userRepository.save(user);

        response.put("success", true);
        response.put("message", "Signup successful!");
        response.put("user", user);

        return response;
    }

   @PostMapping("/login")
    public Map<String, Object> login(@RequestBody LoginRequest request) {
        Map<String, Object> response = new HashMap<>();

        User existingUser = userRepository.findByEmail(request.getEmail()).orElse(null);

        if (existingUser == null) {
            response.put("success", false);
            response.put("message", "User does not exist!");
            return response;
        }

        if (!passwordEncoder.matches(request.getPassword(), existingUser.getPassword())) {
            response.put("success", false);
            response.put("message", "Invalid password!");
            return response;
        }

        // 🔥 Remove role validation (because user already has a stored role)
        // User cannot choose role during login.

        // 🔥 Normalize role for Spring Security (match exactly what SecurityConfig expects)
        String normalizedRole = existingUser.getRole().equalsIgnoreCase("admin") ? "Admin" :
                                existingUser.getRole().equalsIgnoreCase("student") ? "Student" :
                                "USER"; // default fallback

        existingUser.setRole(normalizedRole);

        String token = jwtUtil.generateToken(existingUser.getEmail(), normalizedRole);

        response.put("success", true);
        response.put("message", "Login successful!");
        response.put("token", token);
        response.put("user", Map.of(
                "name", existingUser.getName(),
                "email", existingUser.getEmail(),
                "role", normalizedRole
        ));

        return response;
    }

}








