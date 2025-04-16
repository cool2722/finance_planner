package com.example.domain.account;

import com.example.infrastructure.account.PasswordHasher;

import java.util.Objects;

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
// Compare the Hashes, not the Password itself
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Password password)) return false;
        return hashed.equals(password.hashed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hashed);
    }
}
