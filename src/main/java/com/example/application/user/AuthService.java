package com.example.application.user;

import java.util.Objects;

import org.springframework.stereotype.Service;

import com.example.domain.account.Username;
import com.example.domain.account.Password;
import com.example.domain.user.User;
import com.example.domain.user.UserRepository;
import com.example.infrastructure.account.JwtUtil;

@Service
public class AuthService {
    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = Objects.requireNonNull(userRepository, "UserRepository must not be null"); // Redundant?
    }

    public User register(String username, String rawPassword) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username already in use");
        }
        Username usernameVO = new Username(username);
        Password passwordVO = new Password(rawPassword);
        User user = new User(usernameVO, passwordVO);
        return userRepository.save(user);
    }

    public String login(String username, String rawPassword) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!user.getPassword().matches(rawPassword)) {
            throw new RuntimeException("Invalid credentials");
        }

        return JwtUtil.generateToken(user);
    }
}
