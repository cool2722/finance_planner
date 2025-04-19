package com.example.infrastructure.transaction;

import com.example.domain.transaction.TransactionRepositoryInterface;
import com.example.domain.transaction.TransactionType;
import com.example.infrastructure.transaction.TransactionRepository;
import com.example.domain.transaction.RepeatType;
import com.example.domain.transaction.Transaction;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

@Repository
public class TransactionRepository implements TransactionRepositoryInterface {
    private final Map<String, List<Transaction>> store = new ConcurrentHashMap<>();

    @Override
    public Transaction save(Transaction transaction) {
        store.computeIfAbsent(transaction.getUsername(), k -> new ArrayList<>()).add(transaction);
        System.out.println("Saving transaction for user: " + transaction.getUsername());
        return transaction;
    }

    @Override
    public List<Transaction> findLastNByUsername(String username, int n) {
        System.out.println("Looking up transactions for user: " + username);
        System.out.println("Found: " + getUserTransactions(username).size());

        return getUserTransactions(username).stream()
                .sorted(Comparator.comparing(Transaction::getTimestamp).reversed())
                .limit(Math.min(n, 50))
                .collect(Collectors.toList());
    }

    @Override
    public List<Transaction> findRecentByUsername(String username) {
        return findLastNByUsername(username, 5); 
    }

    @Override
    public List<Transaction> findByUsernameFiltered(String username, TransactionType type, RepeatType repeatType) {
        return getUserTransactions(username).stream()
                .filter(tx -> type == null || tx.getType() == type)
                .filter(tx -> repeatType == null || tx.getRepeatType() == repeatType)
                .collect(Collectors.toList());
    }

    private List<Transaction> getUserTransactions(String username) {
        return store.getOrDefault(username, Collections.emptyList());
    }
}
