package com.example.domain.account;

public class Email {
    private final String value;

    public Email(String value) {
        if (!value.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new IllegalArgumentException("Invalid email format");
        }
        this.value = value.toLowerCase();
    }

    public String getValue() {
        return value;
    }
}