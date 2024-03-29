package com.example.library.controller;

import com.example.library.entity.Author;
import com.example.library.service.AuthorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthorController.class)
public class AuthorControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AuthorService authorService;
    ObjectMapper objectMapper = new ObjectMapper();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @BeforeAll
    public static void setTimeZone() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    @Test
    public void getAllAuthorsTest() throws Exception {
        when(authorService.getAllAuthor())
                .thenReturn(List.of(new Author(1, "John", sdf.parse("2022-02-19"), "USA"),
                        new Author(2, "Jack", sdf.parse("2022-08-04"), "France")));

        mockMvc.perform(get("/api/authors")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("John"))
                .andExpect(jsonPath("$[0].birthDate").value("2022-02-19T00:00:00.000+00:00"))
                .andExpect(jsonPath("$[0].country").value("USA"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Jack"))
                .andExpect(jsonPath("$[1].birthDate").value("2022-08-04T00:00:00.000+00:00"))
                .andExpect(jsonPath("$[1].country").value("France"));
    }

    @Test
    public void getAuthorByIdTest() throws Exception {
        when(authorService.getAuthor(anyInt()))
                .thenReturn(new Author(1, "John", sdf.parse("2022-11-11"), "USA"));

        mockMvc.perform(get("/api/authors/{id}", anyInt())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.birthDate").value("2022-11-11T00:00:00.000+00:00"))
                .andExpect(jsonPath("$.country").value("USA"));
    }

    @Test
    public void createAuthorTest() throws Exception {
        Author author = new Author(1, "John", sdf.parse("2022-11-11"), "USA");
        String jsonAuthor = objectMapper.writeValueAsString(author);

        when(authorService.addAuthor(any()))
                .thenReturn(author);

        mockMvc.perform(post("/api/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonAuthor)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.birthDate").value("2022-11-11T00:00:00.000+00:00"))
                .andExpect(jsonPath("$.country").value("USA"));
    }

    @Test
    public void updateAuthorTest() throws Exception {
        Author author = new Author(1, "John", sdf.parse("2022-11-11"), "USA");
        String jsonAuthor = objectMapper.writeValueAsString(author);

        when(authorService.updateAuthor(anyInt(), any()))
                .thenReturn(author);

        mockMvc.perform(put("/api/authors/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonAuthor))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.birthDate").value("2022-11-11T00:00:00.000+00:00"))
                .andExpect(jsonPath("$.country").value("USA"));
    }

    @Test
    public void deleteAuthorTest() throws Exception {
        when(authorService.deleteAuthor(anyInt()))
                .thenReturn(new Author(1, "John", sdf.parse("2022-11-11"), "USA"));

        mockMvc.perform(delete("/api/authors/{id}", anyInt())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.birthDate").value("2022-11-11T00:00:00.000+00:00"))
                .andExpect(jsonPath("$.country").value("USA"));
    }

    @Test
    public void deleteAllAuthorsTest() throws Exception {
        mockMvc.perform(delete("/api/authors"))
                .andExpect(status().isNoContent());
        verify(authorService).deleteAll();
    }
}
