package com.example.application.report;

import com.example.domain.transaction.TransactionRepositoryInterface;
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
        TransactionRepositoryInterface repo = mock(TransactionRepositoryInterface.class);

        List<Transaction> mockedTransactions = List.of(
                Transaction.builder()
                        .withUsername("user1")
                        .withTimestamp(LocalDateTime.now())
                        .withReference("Netflix")
                        .withMoney(new Money(new BigDecimal("10.00"), Currency.USD))
                        .withType(TransactionType.EXPENSE)
                        .withRepeatType(RepeatType.MONTHLY)
                        .withSentTo("Netflix Inc")
                        .withSentFrom("user1")
                        .build());

        when(repo.findLastNByUsername("user1", 100)).thenReturn(mockedTransactions);

        PdfReportBuilder pdfReportBuilder = mock(PdfReportBuilder.class);
        when(pdfReportBuilder.buildWithChart(any(), any(), any(), any()))
                .thenReturn(new byte[] { 1, 2, 3 });

        GenerateReportService service = new GenerateReportService(repo, pdfReportBuilder);
        byte[] pdfBytes = service.generateReport("user1");

        assertNotNull(pdfBytes, "PDF bytes should not be null");
        assertTrue(pdfBytes.length > 0, "Generated PDF should not be empty");
    }
}