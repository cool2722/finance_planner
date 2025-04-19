package com.example.application.transaction;

import com.example.domain.transaction.Money;
import com.example.domain.transaction.TransactionRepositoryInterface;
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
        TransactionRepositoryInterface repo = mock(TransactionRepositoryInterface.class);
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
        TransactionRepositoryInterface repo = mock(TransactionRepositoryInterface.class);
        CreateTransactionService service = new CreateTransactionService(repo);

        assertThrows(IllegalArgumentException.class, () -> service.create("user1", null));
    }

    @Test
    void shouldDefaultRepeatTypeToNoneIfNotProvided() {
    Transaction tx = Transaction.builder()
        .withUsername("user1")
        .withMoney(new Money(new BigDecimal("12.34"), Currency.USD))
        .withReference("Lunch")
        .withType(TransactionType.EXPENSE)
        .withSentTo("McDonald's")
        .withSentFrom("user1")
        .build();

    assertEquals(RepeatType.NONE, tx.getRepeatType(), "RepeatType should default to NONE");
}

    @Test
    void shouldSetSentToOrSentFromBasedOnTransactionType() {
    Transaction income = Transaction.builder()
        .withUsername("user1")
        .withMoney(new Money(new BigDecimal("100.00"), Currency.USD))
        .withType(TransactionType.INCOME)
        .withReference("Salary")
        .withSentFrom("Company XYZ")
        .build();

    assertEquals("Company XYZ", income.getSentFrom());
    assertEquals("user1", income.getSentTo());

    Transaction expense = Transaction.builder()
        .withUsername("user1")
        .withMoney(new Money(new BigDecimal("20.00"), Currency.USD))
        .withType(TransactionType.EXPENSE)
        .withReference("Dinner")
        .withSentTo("Restaurant")
        .build();

    assertEquals("Restaurant", expense.getSentTo());
    assertEquals("user1", expense.getSentFrom());
}

    @Test
    void shouldThrowIfUsernameIsNull() {
    Exception exception = assertThrows(NullPointerException.class, () ->
        Transaction.builder()
            .withMoney(new Money(new BigDecimal("10.00"), Currency.USD))
            .withType(TransactionType.EXPENSE)
            .withSentTo("Netflix")
            .build()
    );

    assertEquals("Must assign to a user", exception.getMessage());
}

    @Test
    void shouldAssignNowIfTimestampNotProvided() {
    LocalDateTime before = LocalDateTime.now();

    Transaction tx = Transaction.builder()
        .withUsername("user1")
        .withMoney(new Money(new BigDecimal("25.00"), Currency.USD))
        .withType(TransactionType.EXPENSE)
        .withSentTo("Bookstore")
        .build();

    LocalDateTime after = LocalDateTime.now();

    assertNotNull(tx.getTimestamp(), "Timestamp should be set");
    assertTrue(!tx.getTimestamp().isBefore(before) && !tx.getTimestamp().isAfter(after),
            "Timestamp should be within the current time window");
}

}
