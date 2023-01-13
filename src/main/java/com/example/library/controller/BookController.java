package com.example.library.controller;

import com.example.library.entity.Author;
import com.example.library.entity.Book;
import com.example.library.entity.Category;
import com.example.library.service.AuthorService;
import com.example.library.service.BookService;
import com.example.library.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;
    private final CategoryService categoryService;

    @Autowired
    public BookController(BookService bookService, AuthorService authorService, CategoryService categoryService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.categoryService = categoryService;
    }

    @GetMapping("/books")
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.getAllBook();
        if (books.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable("id") int id) {
        Optional<Book> book = bookService.getBook(id);
        if (book.isPresent()) {
            return new ResponseEntity<>(book.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/authors/{id}/books")
    public ResponseEntity<List<Book>> getAllBooksByAuthorId(@PathVariable(value = "id") int id) {
        Optional<Author> author = authorService.getAuthor(id);
        if (author.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Book> books = bookService.findBooksByAuthorsId(id);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/books/{id}/authors")
    public ResponseEntity<List<Author>> getAllAuthorsByBookId(@PathVariable(value = "id") int id) {
        Optional<Book> book = bookService.getBook(id);
        if (book.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Author> authors = authorService.findAuthorsByBooksId(id);
        return new ResponseEntity<>(authors, HttpStatus.OK);
    }

    @GetMapping("/categories/{id}/books")
    public ResponseEntity<List<Book>> getAllBooksByCategoryId(@PathVariable(value = "id") int id) {
        Optional<Category> category = categoryService.getCategory(id);
        if (category.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Book> books = bookService.findBooksByCategoryId(id);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @PostMapping("/categories/{id}/books")
    public ResponseEntity<Book> createBook(@PathVariable(value = "id") int id, @RequestBody Book newBook) {
        Optional<Category> category = categoryService.getCategory(id);
        if (category.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        newBook.setCategory(category.get());
        return new ResponseEntity<>(bookService.addBook(newBook), HttpStatus.CREATED);
    }

    @DeleteMapping("/categories/{id}/books")
    public ResponseEntity<HttpStatus> deleteAllBooksOfCategory(@PathVariable(value = "id") int id) {
        Optional<Category> category = categoryService.getCategory(id);
        if (category.isPresent()) {
            bookService.deleteBooksByCategoryId(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable("id") int id, @RequestBody Book newBook) {
        Optional<Book> book = bookService.getBook(id);
        if (book.isPresent()) {
            newBook.setId(id);
            newBook.setCategory(book.get().getCategory());
            return new ResponseEntity<>(bookService.updateBook(newBook), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<HttpStatus> deleteBook(@PathVariable("id") int id) {
        Optional<Book> book = bookService.deleteBook(id);
        if (book.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/books")
    public ResponseEntity<HttpStatus> deleteAllBooks() {
        bookService.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
