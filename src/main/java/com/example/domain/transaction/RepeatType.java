package com.example.domain.transaction;

public enum RepeatType {
    NONE, MONTHLY, YEARLY;
    
    public static RepeatType fromCode(String code) {
        return switch (code.toUpperCase()) {
            case "M" -> MONTHLY;
            case "Y" -> YEARLY;
            default -> NONE;
        };
    }
}