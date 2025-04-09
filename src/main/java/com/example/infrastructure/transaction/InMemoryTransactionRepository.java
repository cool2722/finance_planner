package com.example.infrastructure.transaction;

import com.example.domain.transaction.Transaction;
import com.example.domain.transaction.TransactionRepository;
import com.example.domain.transaction.TransactionType;
import com.example.domain.transaction.RepeatType;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class InMemoryTransactionRepository implements TransactionRepository {
    private final Map<String, List<Transaction>> store = new ConcurrentHashMap<>();

    @Override
    public Transaction save(Transaction transaction) {
        store.computeIfAbsent(transaction.getUserId(), k -> new ArrayList<>()).add(transaction);
        return transaction;
    }

    @Override
    public List<Transaction> findLastNByUserId(String userId, int n) {
        return getUserTransactions(userId).stream()
                .sorted(Comparator.comparing(Transaction::getTimestamp).reversed())
                .limit(Math.min(n, 50))
                .collect(Collectors.toList());
    }

    @Override
    public List<Transaction> findByUserIdFiltered(String userId, TransactionType type, RepeatType repeatType) {
        return getUserTransactions(userId).stream()
                .filter(tx -> type == null || tx.getType() == type)
                .filter(tx -> repeatType == null || tx.getRepeatType() == repeatType)
                .collect(Collectors.toList());
    }

    private List<Transaction> getUserTransactions(String userId) {
        return store.getOrDefault(userId, Collections.emptyList());
    }

    @Override
    public List<Transaction> findLastFiveByUserId(String userId) {
        return getUserTransactions(userId).stream()
                .sorted(Comparator.comparing(Transaction::getTimestamp).reversed())
                .limit(5)
                .collect(Collectors.toList());
    }
}
