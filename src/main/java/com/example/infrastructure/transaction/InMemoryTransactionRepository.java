package com.example.infrastructure.transaction;

import com.example.domain.transaction.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

@Repository
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
    public List<Transaction> findRecentByUserId(String userId) {
        return findLastNByUserId(userId, 5); 
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
    } // The DRY Principle
}
