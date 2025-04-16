package com.example.web.dto;

import com.example.domain.transaction.Money;
import com.example.domain.transaction.TransactionType;

import io.micrometer.common.lang.Nullable;
import jakarta.validation.constraints.NotNull;

import com.example.domain.transaction.RepeatType;
import java.time.LocalDateTime;

public class TransactionRequest {
    @Nullable
    public LocalDateTime time;

    @Nullable
    public String reference;

    @NotNull
    public Money money;

    @NotNull
    public TransactionType type;

    @Nullable
    public RepeatType repeatType;

    @Nullable
    public String sentTo;

    @Nullable
    public String sentFrom;
}

