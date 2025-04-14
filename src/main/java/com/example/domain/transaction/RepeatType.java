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

    public boolean isRecurring() {
        return this != NONE;
    }
} // Currently does not make sense in yearly recursion, NO LOGIC to handle explicitly the time distance between the recurring transaction
