package com.example.application.transaction;

import java.util.Objects;

import org.springframework.stereotype.Service;

import com.example.domain.transaction.Transaction;
import com.example.domain.transaction.TransactionRepository;
import com.example.web.dto.TransactionRequest;

@Service
public class CreateTransactionService {
    private final TransactionRepository transactionRepository;

    public CreateTransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = Objects.requireNonNull(transactionRepository, "TransactionRepository is required");
    }

    public Transaction create(String username, TransactionRequest req) {
        if (req == null) {
            throw new IllegalArgumentException("TransactionRequest cannot be null");
        }
        Transaction tx = new Transaction(
            username,
            req.time,
            req.reference,
            req.money,
            req.type,
            req.repeatType,
            req.sentTo,
            req.sentFrom
        );
        return transactionRepository.save(tx);
    }    
} 