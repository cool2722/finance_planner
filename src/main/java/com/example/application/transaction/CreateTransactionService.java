package com.example.application.transaction;

import java.util.Objects;

import org.springframework.stereotype.Service;

import com.example.domain.transaction.Transaction;
import com.example.domain.transaction.TransactionRepositoryInterface;
import com.example.web.dto.TransactionRequest;

@Service
public class CreateTransactionService {
    private final TransactionRepositoryInterface transactionRepository;

    public CreateTransactionService(TransactionRepositoryInterface transactionRepository) {
        this.transactionRepository = Objects.requireNonNull(transactionRepository, "TransactionRepository is required");
    }

    public Transaction create(String username, TransactionRequest req) {
        if (req == null) {
            throw new IllegalArgumentException("TransactionRequest cannot be null");
        }
        Transaction tx = Transaction.builder()
        .withUsername(username)
        .withMoney(req.money)
        .withTimestamp(req.time)
        .withReference(req.reference)
        .withType(req.type)
        .withRepeatType(req.repeatType)
        .withSentTo(req.sentTo)
        .withSentFrom(req.sentFrom)
        .build();
        return transactionRepository.save(tx);
    }    
} 