package com.example.infrastructure.user;

import com.example.domain.user.User;
import com.example.domain.user.UserRepository;

import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private final Map<String, User> users = new HashMap<>();

    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(users.get(username.toLowerCase()));
    }

    @Override
    public User save(User user) {
        users.put(user.getUsername().getValue(), user);
        return user;
    }
}
