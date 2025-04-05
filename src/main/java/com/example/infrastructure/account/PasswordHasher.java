package com.example.infrastructure.account;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHasher {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static String hash(String raw) {
        return encoder.encode(raw);
    }

    public static boolean matches(String raw, String hashed) {
        return encoder.matches(raw, hashed);
    }
}