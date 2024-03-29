package com.example.library.controller;

import com.example.library.entity.Category;
import com.example.library.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategory();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable("id") int id) {
        Category category = categoryService.getCategory(id);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @PostMapping("/categories")
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        return new ResponseEntity<>(categoryService.addCategory(category), HttpStatus.CREATED);
    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable("id") int id, @RequestBody Category newCategory) {
        Category category = categoryService.updateCategory(id, newCategory);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<Category> deleteCategory(@PathVariable("id") int id) {
        Category category = categoryService.deleteCategory(id);
        return new ResponseEntity<>(category, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/categories")
    public ResponseEntity<HttpStatus> deleteAllCategories() {
        categoryService.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
