package com.example.library.service;

import com.example.library.entity.Category;
import com.example.library.repo.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategory() {
        return (List<Category>) categoryRepository.findAll();
    }

    public Category getCategory(Integer id) {
        return categoryRepository.findById(id).orElse(null);
    }

    public Category updateBook(Category category) {
        categoryRepository.save(category);
        return category;
    }

    public Category deleteCategory(Integer id) {
        Category category = categoryRepository.findById(id).orElse(null);
        categoryRepository.deleteById(id);
        return category;
    }
}
