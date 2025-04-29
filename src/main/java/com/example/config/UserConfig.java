package com.example.config;

import com.example.application.user.AuthService;
import com.example.domain.user.UserRepositoryInterface;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {

    @Bean
    public AuthService authService(UserRepositoryInterface userRepository) {
        return new AuthService(userRepository);
    }
}