package com.example.domain.transaction;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoneyTest {

    @Test
    void shouldRoundDownFromThirdDecimal() {
        Money money = new Money(new BigDecimal("10.999"), Currency.EUR);
        assertEquals("10.99", money.getValue().toString());
    }

    @Test
    void shouldRoundOneDecimalToTwo() {
        Money money = new Money(new BigDecimal("12.1"), Currency.EUR);
        assertEquals("12.10", money.getValue().toString());
    }

    @Test
    void shouldRound24Point321To24Point32() {
        Money money = new Money(new BigDecimal("24.225"), Currency.EUR);
        assertEquals("24.22", money.getValue().toString());
    }

    @Test
    void shouldRejectZeroAmount() {
        assertThrows(IllegalArgumentException.class, () ->
                new Money(BigDecimal.ZERO, Currency.USD));
    }

    @Test
    void shouldRejectNullValue() {
        assertThrows(IllegalArgumentException.class, () ->
                new Money(null, Currency.USD));
    }

    @Test
    void shouldRejectNullCurrency() {
        assertThrows(IllegalArgumentException.class, () ->
                new Money(new BigDecimal("10"), null));
    }
}
