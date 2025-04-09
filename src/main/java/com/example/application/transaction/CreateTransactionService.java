package com.example.application.transaction;

import com.example.domain.transaction.Transaction;
import com.example.domain.transaction.TransactionRepository;

public class CreateTransactionService {
    private final TransactionRepository transactionRepository;

    public CreateTransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction create(Transaction transaction) {
        return transactionRepository.save(transaction);
    }
} 