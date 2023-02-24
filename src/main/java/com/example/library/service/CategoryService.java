package com.example.library.service;

import com.example.library.entity.Category;
import com.example.library.exeptions.CategoryAlreadyExistException;
import com.example.library.exeptions.CategoryNotFoundException;
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
        if (category.getId() != null) {
            throw new CategoryAlreadyExistException("Category with id:" + category.getId() + " already exists");
        }
        return categoryRepository.save(category);
    }

    public List<Category> getAllCategory() {
        List<Category> categoryList = (List<Category>) categoryRepository.findAll();
        if (categoryList.isEmpty()) {
            throw new CategoryNotFoundException("No categories found");
        }
        return categoryList;
    }

    public Category getCategory(Integer id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isEmpty()) {
            throw new CategoryNotFoundException("No category with id: " + id);
        }
        return category.get();
    }

    public Category updateCategory(int id, Category newCategory) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isEmpty()) {
            throw new CategoryNotFoundException("No category found with id: " + id);
        }
        newCategory.setId(id);
        return categoryRepository.save(newCategory);
    }

    public Category deleteCategory(Integer id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isEmpty()) {
            throw new CategoryNotFoundException("No category found with id: " + id);
        }
        categoryRepository.deleteById(id);
        return category.get();
    }

    public void deleteAll() {
        categoryRepository.deleteAll();
    }
}
