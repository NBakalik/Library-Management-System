package com.example.library.service;

import com.example.library.entity.Category;
import com.example.library.repo.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }

    public List<Category> getAllCategory() {
        return (List<Category>) categoryRepository.findAll();
    }

    public Optional<Category> getCategory(Integer id) {
        return categoryRepository.findById(id);
    }

    public Category updateCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Optional<Category> deleteCategory(Integer id) {
        Optional<Category> category = categoryRepository.findById(id);
        categoryRepository.deleteById(id);
        return category;
    }

    public void deleteAll() {
        categoryRepository.deleteAll();
    }
}
