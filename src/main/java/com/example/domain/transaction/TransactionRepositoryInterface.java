package com.example.domain.transaction;

import java.util.List;

public interface TransactionRepositoryInterface {
    Transaction save(Transaction transaction);
    List<Transaction> findRecentByUsername(String username);
    List<Transaction> findLastNByUsername(String username, int n);
    List<Transaction> findByUsernameFiltered(String username, TransactionType type, RepeatType repeatType);
}