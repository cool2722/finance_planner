package com.example.domain.transaction;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

public enum RepeatType {
    NONE {
        @Override
        public LocalDateTime nextOccurrence(LocalDateTime from) {
            return null;
        }
    },
    MONTHLY {
        @Override
        public LocalDateTime nextOccurrence(LocalDateTime from) {
            return from.with(TemporalAdjusters.lastDayOfMonth()).withHour(0).withMinute(0);
        }
    },
    YEARLY {
        @Override
        public LocalDateTime nextOccurrence(LocalDateTime from) {
            return from.withMonth(12)
                       .with(TemporalAdjusters.lastDayOfMonth())
                       .withHour(0).withMinute(0);
        }
    };

    public abstract LocalDateTime nextOccurrence(LocalDateTime from);

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
}
