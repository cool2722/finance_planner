package com.example.domain.transaction;

import java.util.List;

public interface TransactionRepository {
    Transaction save(Transaction transaction);
    List<Transaction> findRecentByUserId(String userId);
    List<Transaction> findLastNByUserId(String userId, int n);
    List<Transaction> findByUserIdFiltered(String userId, TransactionType type, RepeatType repeatType);
}