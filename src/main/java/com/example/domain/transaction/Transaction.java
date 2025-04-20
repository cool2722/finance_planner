package com.example.domain.transaction;

import java.util.Objects;
import java.util.UUID;
import java.time.LocalDateTime;

public class Transaction {
    private final UUID id;
    private final String username;
    private final String reference;
    private final Money money;
    private final TransactionType type;
    private final RepeatType repeatType;
    private final String sentTo;
    private final String sentFrom;
    private final LocalDateTime timestamp;

    private Transaction(Builder builder) {
        this.id = UUID.randomUUID();
        this.username = Objects.requireNonNull(builder.username, "Must assign to a user");
        this.reference = builder.reference != null ? builder.reference : "";
        this.money = Objects.requireNonNull(builder.money, "Money must be assigned to a user");
        this.type = Objects.requireNonNull(builder.type,"Type must be assigned to a user");
        this.repeatType = builder.repeatType != null ? builder.repeatType : RepeatType.NONE;
        this.sentTo = (type.isExpense()) ? builder.sentTo : builder.username;
        this.sentFrom = (type.isIncome()) ? builder.sentFrom : builder.username;
        this.timestamp = builder.timestamp != null ? builder.timestamp : LocalDateTime.now();
    }
    public UUID getId() { return id; }
    public String getUsername() { return username; }
    public String getReference() { return reference; }
    public Money getMoney() { return money; }
    public TransactionType getType() { return type; }
    public RepeatType getRepeatType() { return repeatType; }
    public String getSentTo() { return sentTo; }
    public String getSentFrom() { return sentFrom; }
    public LocalDateTime getTimestamp() { return timestamp; }
    
    
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String username;
        private String reference;
        private Money money;
        private TransactionType type;
        private RepeatType repeatType;
        private String sentTo;
        private String sentFrom;
        private LocalDateTime timestamp;

        public Builder withUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder withReference(String reference) {
            this.reference = reference;
            return this;
        }

        public Builder withMoney(Money money) {
            this.money = money;
            return this;
        }

        public Builder withType(TransactionType type) {
            this.type = type;
            return this;
        }

        public Builder withRepeatType(RepeatType repeatType) {
            this.repeatType = repeatType;
            return this;
        }

        public Builder withSentTo(String sentTo) {
            this.sentTo = sentTo;
            return this;
        }

        public Builder withSentFrom(String sentFrom) {
            this.sentFrom = sentFrom;
            return this;
        }

        public Builder withTimestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Transaction build() {
            return new Transaction(this);
        }
    }
}

