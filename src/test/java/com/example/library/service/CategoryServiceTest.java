package com.example.library.service;

import com.example.library.entity.Category;
import com.example.library.exeptions.CategoryAlreadyExistException;
import com.example.library.exeptions.CategoryNotFoundException;
import com.example.library.repo.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@WebMvcTest(CategoryService.class)
public class CategoryServiceTest {

    @MockBean
    private CategoryRepository categoryRepository;

    @Autowired
    @InjectMocks
    private CategoryService categoryService;

    @Test
    public void getAllCategoriesWhenExistTest() {
        when(categoryRepository.findAll())
                .thenReturn(List.of(new Category(1, "Comedy"), new Category(2, "History")));

        List<Category> categoryList = categoryService.getAllCategory();
        assertThat(categoryList.get(0).getId()).isEqualTo(1);
        assertThat(categoryList.get(0).getName()).isEqualTo("Comedy");
        assertThat(categoryList.get(1).getId()).isEqualTo(2);
        assertThat(categoryList.get(1).getName()).isEqualTo("History");
    }

    @Test
    public void getAllCategoriesWhenNotExistTest() {
        when(categoryRepository.findAll())
                .thenReturn(Collections.emptyList());

        assertThatThrownBy(() -> categoryService.getAllCategory())
                .isInstanceOf(CategoryNotFoundException.class);
    }

    @Test
    public void getCategoryByIdWhenExistTest() {
        when(categoryRepository.findById(anyInt()))
                .thenReturn(Optional.of(new Category(1, "Comedy")));

        Category category = categoryService.getCategory(anyInt());
        assertThat(category.getId()).isEqualTo(1);
        assertThat(category.getName()).isEqualTo("Comedy");
    }

    @Test
    public void getCategoryByIdWhenNotExistTest() {
        when(categoryRepository.findById(anyInt()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoryService.getCategory(anyInt()))
                .isInstanceOf(CategoryNotFoundException.class);
    }

    @Test
    public void createCategoryWithoutIdTest() {
        Category category = new Category("Comedy");
        categoryService.addCategory(category);

        verify(categoryRepository).save(category);
    }

    @Test
    public void createCategoryWithIdTest() {
        Category category = new Category(1, "Comedy");

        assertThatThrownBy(() -> categoryService.addCategory(category))
                .isInstanceOf(CategoryAlreadyExistException.class);

        verifyNoInteractions(categoryRepository);
    }

    @Test
    public void updateCategoryWhenExistTest() {
        Category category = new Category("Comedy");

        when(categoryRepository.findById(anyInt()))
                .thenReturn(Optional.of(category));
        when(categoryRepository.save(category))
                .thenReturn(category);

        category = categoryService.updateCategory(1, category);
        assertThat(category.getId()).isEqualTo(1);
        assertThat(category.getName()).isEqualTo("Comedy");
    }

    @Test
    public void updateCategoryWhenNotExistTest() {
        Category category = new Category("Comedy");

        when(categoryRepository.findById(anyInt()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoryService.updateCategory(1, category))
                .isInstanceOf(CategoryNotFoundException.class);
        verify(categoryRepository).findById(anyInt());
        verifyNoMoreInteractions(categoryRepository);
    }

    @Test
    public void deleteCategoryWhenExistTest() {
        Category category = new Category(1, "Comedy");

        when(categoryRepository.findById(anyInt()))
                .thenReturn(Optional.of(category));
        categoryService.deleteCategory(anyInt());

        verify(categoryRepository).deleteById(anyInt());
    }

    @Test
    public void deleteCategoryWhenNotExistTest() {
        when(categoryRepository.findById(anyInt()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoryService.deleteCategory(anyInt()))
                .isInstanceOf(CategoryNotFoundException.class);

        verify(categoryRepository).findById(anyInt());
        verifyNoMoreInteractions(categoryRepository);
    }

    @Test
    public void deleteAllCategoriesTest() {
        categoryService.deleteAll();
        verify(categoryRepository).deleteAll();
    }
}
