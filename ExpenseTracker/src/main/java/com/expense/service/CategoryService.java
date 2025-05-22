package com.expense.service;

import com.expense.model.Category;
import com.expense.model.TransactionType;
import com.expense.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public List<Category> getCategoriesByType(TransactionType type) {
        return categoryRepository.findByType(type);
    }


    public Category getByName(String name) {
        return categoryRepository.findByName(name).orElse(null);
    }
}
