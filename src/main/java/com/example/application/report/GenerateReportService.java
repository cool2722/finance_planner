package com.example.application.report;

import com.example.domain.transaction.Transaction;
import com.example.domain.transaction.TransactionRepository;
import com.example.infrastructure.report.PdfReportBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

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
                .collect(Collectors.toList());

        List<Transaction> recurringIncomes = recentTx.stream()
        .filter(tx -> tx.getRepeatType().isRecurring() && tx.getType().isIncome())
        .collect(Collectors.toList());
            
        List<Transaction> recurringExpenses = recentTx.stream()
            .filter(tx -> tx.getRepeatType().isRecurring() && tx.getType().isExpense())
            .collect(Collectors.toList());    

        return pdfReportBuilder.build(username, recentTx, recurringExpenses, recurringIncomes);
    }
}
