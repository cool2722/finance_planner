package com.example.application.transaction;

import java.util.Objects;

import org.springframework.stereotype.Service;

import com.example.domain.transaction.Transaction;
import com.example.domain.transaction.TransactionRepository;

@Service
public class CreateTransactionService {
    private final TransactionRepository transactionRepository;

    public CreateTransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = Objects.requireNonNull(transactionRepository, "TransactionRepository is required");
    }

    public Transaction create(Transaction transaction) {
        if (transaction == null) {
            throw new IllegalArgumentException("Transaction must not be null");
        }
        return transactionRepository.save(transaction);
    }
} 