package com.example.infrastructure.transaction;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.List;

import com.example.domain.transaction.*;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTransactionRepositoryTest {
    @Test
    void returnsMax50Transactions() {
        InMemoryTransactionRepository repo = new InMemoryTransactionRepository();
        for (int i = 0; i < 100; i++) {
            repo.save(new Transaction("u1", "T" + i, new Money(new BigDecimal(Double.toString(100 * (Math.random() * i))), Currency.INR), TransactionType.EXPENSE, RepeatType.NONE, "X", "u1"));
            // Code smell with double floating point precisioin?
        }
        List<Transaction> results = repo.findLastNByUserId("u1", 100);
        assertEquals(50, results.size());
    }
}
