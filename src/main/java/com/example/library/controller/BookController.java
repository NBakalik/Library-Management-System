package com.example.library.controller;

import com.example.library.entity.Author;
import com.example.library.entity.Book;
import com.example.library.service.AuthorService;
import com.example.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;

    @Autowired
    public BookController(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @GetMapping("/books")
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.getAllBook();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable("id") int id) {
        Book book = bookService.getBook(id);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @GetMapping("/authors/{id}/books")
    public ResponseEntity<List<Book>> getAllBooksByAuthorId(@PathVariable(value = "id") int id) {
        List<Book> books = bookService.findBooksByAuthorsId(id);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/books/{id}/authors")
    public ResponseEntity<List<Author>> getAllAuthorsByBookId(@PathVariable(value = "id") int id) {
        List<Author> authors = authorService.findAuthorsByBooksId(id);
        return new ResponseEntity<>(authors, HttpStatus.OK);
    }

    @GetMapping("/categories/{id}/books")
    public ResponseEntity<List<Book>> getAllBooksByCategoryId(@PathVariable(value = "id") int id) {
        List<Book> books = bookService.findBooksByCategoryId(id);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @PostMapping("/categories/{id}/books")
    public ResponseEntity<Book> addBookToCategory(@PathVariable(value = "id") int id, @RequestBody Book newBook) {
        Book book = bookService.addBookToCategory(id, newBook);
        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }

    @PostMapping("/authors/{id}/books")
    public ResponseEntity<Book> addBookToAuthor(@PathVariable(value = "id") int id, @RequestBody Book newBook) {
        Book book = bookService.addBookToAuthor(id, newBook);
        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }

    @DeleteMapping("/authors/{authorId}/books/{bookId}")
    public ResponseEntity<HttpStatus> deleteBookFromAuthor(@PathVariable(value = "authorId") int authorId, @PathVariable(value = "bookId") int bookId) {
        authorService.deleteBookFromAuthor(authorId, bookId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/categories/{id}/books")
    public ResponseEntity<HttpStatus> deleteAllBooksOfCategory(@PathVariable(value = "id") int id) {
        bookService.deleteBooksByCategoryId(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable("id") int id, @RequestBody Book newBook) {
        Book book = bookService.updateBook(id, newBook);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable("id") int id) {
        Book book = bookService.deleteBook(id);
        return new ResponseEntity<>(book, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/books")
    public ResponseEntity<HttpStatus> deleteAllBooks() {
        bookService.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
