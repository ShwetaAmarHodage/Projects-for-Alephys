package com.expense.service;

import com.expense.model.Category;
import com.expense.model.Transaction;
import com.expense.repository.CategoryRepository;
import com.expense.repository.TransactionRepository;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private CategoryRepository categoryRepository;


    //  Add income and expenses
    public Transaction addTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    // View monthly summaries

    public List<Transaction> getMonthlySummary(int year, int month) {
        YearMonth ym = YearMonth.of(year, month);
        LocalDate start = ym.atDay(1);
        LocalDate end = ym.atEndOfMonth();
        return transactionRepository.findAllByDateBetween(start, end);
    }

    // Save/load data from a file

    public void loadFromFile(InputStream inputStream) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // Read list of transactions from file
        List<Transaction> transactions = mapper.readValue(inputStream, new TypeReference<List<Transaction>>() {
        });

        // Replace transient categories with managed ones from DB
        for (Transaction t : transactions) {
            Long categoryId = t.getCategory().getId();
            Category managedCategory = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new RuntimeException("Category not found: " + categoryId));
            t.setCategory(managedCategory);
        }


        // Save all transactions
        transactionRepository.saveAll(transactions);
    }
}