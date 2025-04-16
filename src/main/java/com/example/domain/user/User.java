package com.example.domain.user;

import com.example.domain.account.Username;
import com.example.domain.account.Password;

public class User {
    private Username username;
    private Password password;

    public User(Username username, Password password) {
        this.username = username;
        this.password = password;
    }

    public Username getUsername() {
        return username;
    }

    public Password getPassword() {
        return password;
    }
}