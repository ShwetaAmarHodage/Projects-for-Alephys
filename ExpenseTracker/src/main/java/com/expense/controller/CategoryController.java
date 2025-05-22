package com.expense.controller;


import com.expense.model.Category;
import com.expense.model.TransactionType;
import com.expense.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Category> addCategory(@RequestBody Category category) {
        return ResponseEntity.ok(categoryService.addCategory(category));
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories(
            @RequestParam(value = "type", required = false) TransactionType type) {
        if (type != null) {
            return ResponseEntity.ok(categoryService.getCategoriesByType(type));
        } else {
            return ResponseEntity.ok(categoryService.getAllCategories());
        }
    }
}
