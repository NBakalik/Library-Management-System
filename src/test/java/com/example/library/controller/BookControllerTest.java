package com.example.library.controller;

import com.example.library.entity.Author;
import com.example.library.entity.Book;
import com.example.library.entity.Category;
import com.example.library.service.AuthorService;
import com.example.library.service.BookService;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
public class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BookService bookService;
    @MockBean
    private AuthorService authorService;
    ObjectMapper objectMapper = new ObjectMapper();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @BeforeAll
    public static void setTimeZone() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    @Test
    public void getAllBooksTest() throws Exception {
        when(bookService.getAllBook())
                .thenReturn(List.of(new Book(1, "Java 8", new Category("Programming")),
                        new Book(2, "Romeo and Juliet", new Category("Tragedy"))));

        mockMvc.perform(get("/api/books")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Java 8"))
                .andExpect(jsonPath("$[0].category.name").value("Programming"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Romeo and Juliet"))
                .andExpect(jsonPath("$[1].category.name").value("Tragedy"));
    }

    @Test
    public void getBookByIdTest() throws Exception {
        when(bookService.getBook(anyInt()))
                .thenReturn(new Book(1, "Java 8", new Category("Programming")));

        mockMvc.perform(get("/api/books/{id}", anyInt())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Java 8"))
                .andExpect(jsonPath("$.category.name").value("Programming"));
    }


    @Test
    public void getAllBooksByAuthorIdTest() throws Exception {
        when(bookService.findBooksByAuthorsId(anyInt()))
                .thenReturn(List.of(new Book(1, "Java 8", new Category("Programming")),
                        new Book(2, "Romeo and Juliet", new Category("Tragedy"))));

        mockMvc.perform(get("/api/authors/{id}/books", anyInt())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Java 8"))
                .andExpect(jsonPath("$[0].category.name").value("Programming"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Romeo and Juliet"))
                .andExpect(jsonPath("$[1].category.name").value("Tragedy"));
    }


    @Test
    public void getAllAuthorsByBookIdTest() throws Exception {
        when(authorService.findAuthorsByBooksId(anyInt()))
                .thenReturn(List.of(new Author(1, "John", sdf.parse("2022-02-19"), "USA"),
                        new Author(2, "Jack", sdf.parse("2022-08-04"), "France")));

        mockMvc.perform(get("/api/books/{id}/authors", anyInt())
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
    public void getAllBooksByCategoryIdTest() throws Exception {
        when(bookService.findBooksByCategoryId(anyInt()))
                .thenReturn(List.of(new Book(1, "Java 8", new Category("Programming")),
                        new Book(2, "Java 11", new Category("Programming"))));

        mockMvc.perform(get("/api/categories/{id}/books", anyInt())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Java 8"))
                .andExpect(jsonPath("$[0].category.name").value("Programming"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Java 11"))
                .andExpect(jsonPath("$[1].category.name").value("Programming"));
    }


    @Test
    public void addBookToCategoryTest() throws Exception {
        Book book = new Book(1, "Java 8", new Category("Programming"));
        String jsonBook = objectMapper.writeValueAsString(book);

        when(bookService.addBookToCategory(anyInt(), any()))
                .thenReturn(book);

        mockMvc.perform(post("/api/categories/{id}/books", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBook)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Java 8"))
                .andExpect(jsonPath("$.category.name").value("Programming"));
    }

    @Test
    public void addBookToAuthorTest() throws Exception {
        Book book = new Book(1, "Java 8", new Category("Programming"));
        String jsonBook = objectMapper.writeValueAsString(book);

        when(bookService.addBookToAuthor(anyInt(), any()))
                .thenReturn(book);

        mockMvc.perform(post("/api/authors/{id}/books", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBook)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Java 8"))
                .andExpect(jsonPath("$.category.name").value("Programming"));
    }

    @Test
    public void deleteBookFromAuthorTest() throws Exception {
        mockMvc.perform(delete("/api/authors/{authorId}/books/{bookId}", anyInt(), anyInt()))
                .andExpect(status().isNoContent());
        verify(authorService).deleteBookFromAuthor(anyInt(), anyInt());
    }

    @Test
    public void deleteAllBooksOfCategoryTest() throws Exception {
        mockMvc.perform(delete("/api/categories/{id}/books", anyInt()))
                .andExpect(status().isNoContent());
        verify(bookService).deleteBooksByCategoryId(anyInt());
    }

    @Test
    public void updateBookTest() throws Exception {
        Book book = new Book(1, "Java 8", new Category("Programming"));
        String jsonBook = objectMapper.writeValueAsString(book);

        when(bookService.updateBook(anyInt(), any()))
                .thenReturn(book);

        mockMvc.perform(put("/api/books/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBook))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Java 8"))
                .andExpect(jsonPath("$.category.name").value("Programming"));
    }

    @Test
    public void deleteBookTest() throws Exception {
        when(bookService.deleteBook(anyInt()))
                .thenReturn(new Book(1, "Java 8", new Category("Programming")));

        mockMvc.perform(delete("/api/books/{id}", anyInt())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Java 8"))
                .andExpect(jsonPath("$.category.name").value("Programming"));
    }

    @Test
    public void deleteAllBooksTest() throws Exception {
        mockMvc.perform(delete("/api/books"))
                .andExpect(status().isNoContent());
        verify(bookService).deleteAll();
    }
}
