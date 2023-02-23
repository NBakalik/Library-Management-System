package com.example.library.controller;

import com.example.library.entity.Category;
import com.example.library.service.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CategoryService categoryService;
    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void getAllCategoriesTest() throws Exception {
        when(categoryService.getAllCategory())
                .thenReturn(List.of(new Category(1, "Comedy"), new Category(2, "History")));

        mockMvc.perform(get("/api/categories")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Comedy"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("History"));
    }

    @Test
    public void getCategoryByIdTest() throws Exception {
        when(categoryService.getCategory(anyInt()))
                .thenReturn(new Category(1, "Comedy"));

        mockMvc.perform(get("/api/categories/{id}", anyInt())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Comedy"));
    }

    @Test
    public void createCategoryTest() throws Exception {
        Category category = new Category("Comedy");
        String jsonCategory = objectMapper.writeValueAsString(category);

        when(categoryService.addCategory(any()))
                .thenReturn(category);

        mockMvc.perform(post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonCategory)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Comedy"));
    }

    @Test
    public void updateCategoryTest() throws Exception {
        Category category = new Category("Comedy");
        String jsonCategory = objectMapper.writeValueAsString(category);

        when(categoryService.updateCategory(anyInt(), any()))
                .thenReturn(category);

        mockMvc.perform(put("/api/categories/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonCategory))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Comedy"));
    }

    @Test
    public void deleteCategoryTest() throws Exception {
        when(categoryService.deleteCategory(anyInt()))
                .thenReturn(new Category("Comedy"));

        mockMvc.perform(delete("/api/categories/{id}", anyInt())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.name").value("Comedy"));
    }

    @Test
    public void deleteAllCategoriesTest() throws Exception {
        mockMvc.perform(delete("/api/categories"))
                .andExpect(status().isNoContent());
        verify(categoryService).deleteAll();
    }
}
