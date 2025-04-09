package com.example.domain.transaction;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Money {
    private final BigDecimal value;
    private final Currency currency;
    private final boolean isPrincipal;

    public Money(BigDecimal value, Currency currency, boolean isPrincipal) {
        if ((value.compareTo(BigDecimal.ZERO) <= 0) && !isPrincipal) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        this.value = value.setScale(2, RoundingMode.HALF_UP);
        this.currency = currency;
        this.isPrincipal = isPrincipal;
    }

    public BigDecimal getValue() { return value; }
    public Currency getCurrency() { return currency; }
    public boolean isPrincipal() { return isPrincipal; }
}