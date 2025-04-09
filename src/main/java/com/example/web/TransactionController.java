package com.example.web;

import com.example.application.transaction.CreateTransactionService;
import com.example.domain.transaction.RepeatType;
import com.example.domain.transaction.Transaction;
import com.example.domain.transaction.TransactionRepository;
import com.example.domain.transaction.TransactionType;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final CreateTransactionService createTransactionService;
    private final TransactionRepository transactionRepository;

    public TransactionController(CreateTransactionService createTransactionService, TransactionRepository transactionRepository) {
        this.createTransactionService = createTransactionService;
        this.transactionRepository = transactionRepository;
    }

    @PostMapping
    public Transaction addTransaction(@RequestBody Transaction transaction) {
        return createTransactionService.create(transaction);
    }

    @GetMapping("/recent")
    public List<Transaction> getRecent(@RequestParam String userId, @RequestParam(defaultValue = "5") int limit) {
        return transactionRepository.findLastNByUserId(userId, limit);
    }

    @GetMapping("/search")
    public List<Transaction> search(
        @RequestParam String userId,
        @RequestParam TransactionType type,
        @RequestParam RepeatType repeatType
    ) {
        return transactionRepository.findByUserIdFiltered(userId, type, repeatType);
    }
}
