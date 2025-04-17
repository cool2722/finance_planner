package com.example.application.report;

import com.example.domain.transaction.TransactionRepository;
import com.example.domain.transaction.Money;
import com.example.domain.transaction.RepeatType;
import com.example.domain.transaction.TransactionType;
import com.example.domain.transaction.Transaction;
import com.example.infrastructure.report.PdfReportBuilder;
import com.example.domain.transaction.Currency;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GenerateReportServiceTest {

    @Test
void buildsReportWithoutError() {
    TransactionRepository repo = mock(TransactionRepository.class);

    List<Transaction> mockedTransactions = List.of(
        new Transaction(
            "user1",
            LocalDateTime.now(),
            "Netflix",
            new Money(new BigDecimal("10.00"), Currency.USD),
            TransactionType.EXPENSE,
            RepeatType.MONTHLY,
            "Netflix Inc",
            null
        )
    );
    when(repo.findLastNByUsername("user1", 100)).thenReturn(mockedTransactions);

    PdfReportBuilder pdfReportBuilder = mock(PdfReportBuilder.class);
    when(pdfReportBuilder.buildWithChart(any(), any(), any(), any()))
        .thenReturn(new byte[] {1, 2, 3});

    GenerateReportService service = new GenerateReportService(repo, pdfReportBuilder);

    byte[] pdfBytes = service.generateQuarterlyReport("user1");

    assertNotNull(pdfBytes, "PDF bytes should not be null");
    assertTrue(pdfBytes.length > 0, "Generated PDF should not be empty");
}
}
