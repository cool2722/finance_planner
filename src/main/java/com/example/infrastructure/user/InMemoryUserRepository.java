package com.example.infrastructure.user;

import com.example.domain.user.User;
import com.example.domain.user.UserRepository;

import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private final Map<String, User> users = new HashMap<>();

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(users.get(email.toLowerCase()));
    }

    @Override
    public User save(User user) {
        users.put(user.getEmail().getValue(), user);
        return user;
    }
}
