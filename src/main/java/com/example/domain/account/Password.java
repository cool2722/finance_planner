package com.example.domain.account;

import com.example.infrastructure.account.PasswordHasher;

public class Password {
    private final String hashed;

    public Password(String rawPassword) {
        this.hashed = PasswordHasher.hash(rawPassword);
    }

    public String getHashed() {
        return hashed;
    }

    public boolean matches(String raw) {
        return PasswordHasher.matches(raw, this.hashed);
    }
}