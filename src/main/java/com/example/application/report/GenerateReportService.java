package com.example.application.report;

import com.example.domain.transaction.Transaction;
import com.example.domain.transaction.TransactionRepository;
import com.example.infrastructure.report.PdfReportBuilder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class GenerateReportService {

    private final TransactionRepository transactionRepository;
    private final PdfReportBuilder pdfReportBuilder;

    public GenerateReportService(TransactionRepository transactionRepository, PdfReportBuilder pdfReportBuilder) {
        this.transactionRepository = transactionRepository;
        this.pdfReportBuilder = pdfReportBuilder;
    }

    public byte[] generateQuarterlyReport(String username) {
        LocalDateTime threeMonthsAgo = LocalDateTime.now().minusMonths(3);
        List<Transaction> allTx = transactionRepository.findLastNByUsername(username, 100);
        List<Transaction> recentTx = allTx.stream()
                .filter(tx -> tx.getTimestamp().isAfter(threeMonthsAgo))
                .toList();

        if (recentTx.isEmpty()) {
            return new byte[0];
        }

        Map<LocalDate, BigDecimal> incomePerDay = new TreeMap<>();
        Map<LocalDate, BigDecimal> expensePerDay = new TreeMap<>();

        for (Transaction tx : recentTx) {
            LocalDate date = tx.getTimestamp().toLocalDate();
            BigDecimal amount = tx.getMoney().getValue();

            if (tx.getType().isIncome()) {
                incomePerDay.merge(date, amount, BigDecimal::add);
            } else if (tx.getType().isExpense()) {
                expensePerDay.merge(date, amount, BigDecimal::add);
            }
        }

        return pdfReportBuilder.buildWithChart(username, incomePerDay, expensePerDay, recentTx);
    }
}
