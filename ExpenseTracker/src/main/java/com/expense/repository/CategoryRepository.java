package com.expense.repository;

import com.expense.model.Category;
import com.expense.model.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByType(TransactionType type);

    Optional<Category> findByName(String name);
}
