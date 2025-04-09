package com.example.domain.transaction;

import java.util.UUID;
import java.time.LocalDateTime;

public class Transaction {
    private final UUID id;
    private final String userId;
    private final String reference;
    private final Money amount;
    private final TransactionType type;
    private final RepeatType repeatType;
    private final String sentTo;
    private final String sentFrom;
    private final LocalDateTime timestamp;

    public Transaction(String userId, String reference, Money amount, TransactionType type, RepeatType repeatType, String sentTo, String sentFrom) {
        this.id = UUID.randomUUID();
        this.userId = userId;
        this.reference = reference;
        this.amount = amount;
        this.type = type;
        this.repeatType = repeatType != null ? repeatType : RepeatType.NONE;
        this.sentTo = (type == TransactionType.EXPENSE) ? sentTo : null;
        this.sentFrom = (type == TransactionType.INCOME) ? sentFrom : null;
        this.timestamp = LocalDateTime.now();
    }

    public UUID getId() { return id; }
    public String getUserId() { return userId; }
    public String getReference() { return reference; }
    public Money getAmount() { return amount; }
    public TransactionType getType() { return type; }
    public RepeatType getRepeatType() { return repeatType; }
    public String getSentTo() { return sentTo; }
    public String getSentFrom() { return sentFrom; }
    public LocalDateTime getTimestamp() { return timestamp; }
}