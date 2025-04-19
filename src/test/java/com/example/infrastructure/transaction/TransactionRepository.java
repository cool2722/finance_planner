package com.example.infrastructure.transaction;

import org.junit.jupiter.api.Test;

import com.example.domain.transaction.Currency;
import com.example.domain.transaction.Money;
import com.example.domain.transaction.RepeatType;
import com.example.domain.transaction.Transaction;
import com.example.domain.transaction.TransactionType;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TransactionRepositoryTest {
    @Test
    void returnsMax50Transactions() {
        TransactionRepository repo = new TransactionRepository();
        for (int i = 1; i < 100; i++) {
            repo.save(new Transaction("u1",null ,"T" + i, new Money(new BigDecimal(Double.toString(0.01 + (100 * (Math.random() * i)))), Currency.INR), TransactionType.EXPENSE, RepeatType.NONE, "X", "u1"));
            // Code smell with double floating point precisioin?
        }
        List<Transaction> results = repo.findLastNByUsername("u1", 100);
        assertEquals(50, results.size());
    }
}
