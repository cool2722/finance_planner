package com.example.domain.user;

import java.util.Optional;

public interface UserRepositoryInterface {
    Optional<User> findByUsername(String username);
    User save(User user);
}