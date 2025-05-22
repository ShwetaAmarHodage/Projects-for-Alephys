package com.expense.controller;

import com.expense.model.Transaction;
import com.expense.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/transaction")
    public ResponseEntity<Transaction> addTransaction(@RequestBody Transaction transaction) {
        return ResponseEntity.ok(transactionService.addTransaction(transaction));
    }

    @GetMapping("/summary/{year}/{month}")
    public ResponseEntity<List<Transaction>> getMonthlySummary(@PathVariable int year, @PathVariable int month) {
        return ResponseEntity.ok(transactionService.getMonthlySummary(year, month));
    }

    @PostMapping("/load-file")
    public ResponseEntity<String> loadFromFile(@RequestParam("file") MultipartFile file) {
        try {
            transactionService.loadFromFile(file.getInputStream());
            return ResponseEntity.ok("File loaded successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to load file: " + e.getMessage());
        }
    }
}