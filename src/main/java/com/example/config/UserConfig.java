package com.example.config;

import com.example.application.user.AuthService;
import com.example.domain.user.UserRepository;
import com.example.infrastructure.user.InMemoryUserRepository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {

    @Bean
    public UserRepository userRepository() {
        return new InMemoryUserRepository();
    }

    @Bean
    public AuthService authService(UserRepository userRepository) {
        return new AuthService(userRepository);
    }
}