package com.example.config;

import com.example.application.user.AuthService;
import com.example.domain.user.UserRepositoryInterface;
import com.example.infrastructure.user.UserRepository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {

    @Bean
    public UserRepositoryInterface userRepository() {
        return new UserRepository();
    }

    @Bean
    public AuthService authService(UserRepositoryInterface userRepository) {
        return new AuthService(userRepository);
    }
}