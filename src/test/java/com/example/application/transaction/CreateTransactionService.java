package com.example.application.transaction;

import com.example.domain.transaction.Money;
import com.example.domain.transaction.TransactionRepository;
import com.example.domain.transaction.Transaction;
import com.example.domain.transaction.RepeatType;
import com.example.domain.transaction.Currency;
import com.example.domain.transaction.TransactionType;

import com.example.web.dto.TransactionRequest;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateTransactionServiceTest {

    @Test
    void shouldCreateTransactionFromRequest() {
        TransactionRepository repo = mock(TransactionRepository.class);
        CreateTransactionService service = new CreateTransactionService(repo);

        TransactionRequest req = new TransactionRequest();
        req.time = LocalDateTime.of(2025, 4, 10, 12, 0);
        req.reference = "Netflix";
        req.money = new Money(new BigDecimal("10.00"), Currency.USD);
        req.type = TransactionType.EXPENSE;
        req.repeatType = RepeatType.MONTHLY;
        req.sentTo = "Netflix Inc";
        req.sentFrom = null; // not needed for expense

        when(repo.save(any())).thenAnswer(i -> i.getArguments()[0]); // return saved object

        Transaction result = service.create("user1", req);

        assertNotNull(result);
        assertEquals("user1", result.getUsername());
        assertEquals("Netflix Inc", result.getSentTo());
        assertEquals(TransactionType.EXPENSE, result.getType());
        verify(repo).save(any());
    }

    @Test
    void shouldRejectNullRequest() {
        TransactionRepository repo = mock(TransactionRepository.class);
        CreateTransactionService service = new CreateTransactionService(repo);

        assertThrows(IllegalArgumentException.class, () -> service.create("user1", null));
    }
}
