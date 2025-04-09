package com.example.domain.user;

import com.example.domain.account.Email;
import com.example.domain.account.Password;

import java.util.UUID;

public class User {
    private UUID id;
    private Email email;
    private Password password;

    public User(Email email, Password password) {
        this.id = UUID.randomUUID();
        this.email = email;
        this.password = password;
    }

    public UUID getId() {
        return id;
    }

    public Email getEmail() {
        return email;
    }

    public Password getPassword() {
        return password;
    }
}