package com.example.domain.transaction;

import java.util.Objects;
import java.util.UUID;

import java.time.LocalDateTime;

public class Transaction {
    private final UUID id;
    private final String userId;
    private final String reference;
    private final Money money;
    private final TransactionType type;
    private final RepeatType repeatType;
    private final String sentTo;
    private final String sentFrom;
    private final LocalDateTime timestamp;

    public Transaction(String userId,LocalDateTime time ,String reference, Money money, TransactionType type, RepeatType repeatType, String sentTo, String sentFrom) {
        this.id = UUID.randomUUID();
        this.userId = Objects.requireNonNull(userId, "Must assign to a user");;
        this.reference = reference != null ? reference : "";
        this.money = money;
        this.type = type;
        this.repeatType = repeatType != null ? repeatType : RepeatType.NONE;
        this.sentTo = (type.isExpense()) ? sentTo : userId; // If not an Expense, it is necessarily Income, and Income is always sent to user itself
        this.sentFrom = (type.isIncome()) ? sentFrom : userId;
        this.timestamp = time != null ? time : LocalDateTime.now();
    } // sentTo and sentFrom may have smells

    public UUID getId() { return id; }
    public String getUserId() { return userId; }
    public String getReference() { return reference; }
    public Money getMoney() { return money; }
    public TransactionType getType() { return type; }
    public RepeatType getRepeatType() { return repeatType; }
    public String getSentTo() { return sentTo; }
    public String getSentFrom() { return sentFrom; }
    public LocalDateTime getTimestamp() { return timestamp; }
} // Should Refactor Transaction, too many args in constructor without a logical order. Plus some args are optional.
// Replace with builder.