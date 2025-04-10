package com.example.domain.transaction;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class Money {
    private final BigDecimal value;
    private final Currency currency;

    public Money(BigDecimal value, Currency currency) {
        if (value == null || currency == null) {
            throw new IllegalArgumentException("Value and currency cannot be null");
        }
        if (value.signum() <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        this.value = value.setScale(2, RoundingMode.HALF_DOWN);
        this.currency = currency;
    }

    public BigDecimal getValue() {
        return value;
    }

    public Currency getCurrency() {
        return currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Money money))
            return false;
        return value.equals(money.value) && currency == money.currency;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, currency);
    }
}
