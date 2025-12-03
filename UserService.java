package com.smartbus.service;

import com.smartbus.entity.User;
import com.smartbus.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public Optional<User> findByEmail(String email) {
        return repo.findByEmail(email);
    }

    public User save(User user) {
        return repo.save(user);
    }
}
