package com.example.web;

import com.example.application.transaction.CreateTransactionService;
import com.example.domain.transaction.RepeatType;
import com.example.domain.transaction.Transaction;
import com.example.domain.transaction.TransactionRepository;
import com.example.domain.transaction.TransactionType;
import com.example.web.dto.TransactionRequest;
import com.example.infrastructure.account.JwtUtil;

import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> addTransaction(
        @RequestHeader("Authorization") String token,
        @RequestBody TransactionRequest req
    ) {
        String username = JwtUtil.extractUsername(token.replace("Bearer ", ""));
        createTransactionService.create(username, req);
        return ResponseEntity.ok("Transaction saved");
    }


    @GetMapping("/recent")
    public List<Transaction> getRecent(@RequestHeader("Authorization") String token, @RequestParam(defaultValue = "5") int limit) {
        String username = JwtUtil.extractUsername(token.replace("Bearer ", ""));
        return transactionRepository.findLastNByUsername(username, limit);
    }


    @GetMapping("/search")
    public List<Transaction> search(
        @RequestHeader("Authorization") String token, @RequestParam TransactionType type, @RequestParam RepeatType repeatType
    ) {
        String username = JwtUtil.extractUsername(token.replace("Bearer ", ""));
        return transactionRepository.findByUsernameFiltered(username, type, repeatType);
    }
} // Potential Refactor? Maybe Controller shouldnt directly accept Domain models (Transaction)?
//Code smell lifted
