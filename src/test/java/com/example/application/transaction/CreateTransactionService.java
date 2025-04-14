package com.example.application.transaction;

import com.example.domain.transaction.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateTransactionServiceTest {

    @Test
    void shouldCreateTransactionSuccessfully() {
        TransactionRepository repo = mock(TransactionRepository.class);
        CreateTransactionService service = new CreateTransactionService(repo);

        Transaction tx = new Transaction("user1",null, "Netflix",
                new Money(new BigDecimal("10.00"), Currency.USD),
                TransactionType.EXPENSE, RepeatType.MONTHLY,
                "Netflix Inc", null);

        when(repo.save(any())).thenReturn(tx);

        Transaction result = service.create(tx);

        assertNotNull(result);
        assertEquals("user1", result.getUserId());
        verify(repo).save(tx);
    }

    @Test
    void shouldRejectNullTransaction() {
        CreateTransactionService service = new CreateTransactionService(mock(TransactionRepository.class));
        assertThrows(IllegalArgumentException.class, () -> service.create(null));
    }
}
