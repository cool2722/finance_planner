package com.example.domain.transaction;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CurrencyTest {
    @Test
    void rejectsInvalidCurrencyCode() {
        assertThrows(IllegalArgumentException.class, () -> new Money(new BigDecimal("10"), Currency.valueOf("Fake")));
    }
    @Test
    void supportsDefinedCurrencies() {
        assertDoesNotThrow(() -> new Money(new BigDecimal("10"), Currency.valueOf("EUR")));
    }
}
