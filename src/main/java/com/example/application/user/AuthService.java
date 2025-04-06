package com.example.application.user;

import org.springframework.stereotype.Service;

import com.example.domain.account.Email;
import com.example.domain.account.Password;
import com.example.domain.user.User;
import com.example.domain.user.UserRepository;
import com.example.infrastructure.account.JwtUtil;

@Service
public class AuthService {
    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(String email, String rawPassword) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email already in use");
        }
        Email emailVO = new Email(email);
        Password passwordVO = new Password(rawPassword);
        User user = new User(emailVO, passwordVO);
        return userRepository.save(user);
    }

    public String login(String email, String rawPassword) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!user.getPassword().matches(rawPassword)) {
            throw new RuntimeException("Invalid credentials");
        }

        return JwtUtil.generateToken(user);
    }
}
