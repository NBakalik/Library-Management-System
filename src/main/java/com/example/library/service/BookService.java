package com.example.library.service;

import com.example.library.entity.Author;
import com.example.library.entity.Book;
import com.example.library.entity.Category;
import com.example.library.exeptions.BookNotFoundException;
import com.example.library.repo.AuthorRepository;
import com.example.library.repo.BookRepository;
import com.example.library.repo.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public BookService(BookRepository bookRepository,
                       AuthorRepository authorRepository,
                       CategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
    }

    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    public Book addBook(Category category, Book newBook) {
        Optional<String> bookId = Optional.ofNullable(newBook.getId());
        if (bookId.isPresent()) {
            Book book = bookRepository.findById(bookId.get()).orElseThrow(BookNotFoundException::new);
            category.addBook(book);
            categoryRepository.save(category);
            bookRepository.save(book);
            return book;
        }
        category.addBook(newBook);
        bookRepository.save(newBook);
        categoryRepository.save(category);
        return newBook;
    }

    public Book addBook(Author author, Book newBook) {
        Optional<String> bookId = Optional.ofNullable(newBook.getId());
        if (bookId.isPresent()) {
            Book book = bookRepository.findById(bookId.get()).orElseThrow(BookNotFoundException::new);
            author.addBook(book);
            authorRepository.save(author);
            bookRepository.save(book);
            return book;
        }
        author.addBook(newBook);
        bookRepository.save(newBook);
        authorRepository.save(author);
        return newBook;
    }

    public List<Book> getAllBook() {
        return bookRepository.findAll();
    }

    public Optional<Book> getBook(String id) {
        return bookRepository.findById(id);
    }

    public Book updateBook(Book book) {
        return bookRepository.save(book);
    }

    public Optional<Book> deleteBook(String id) {
        Optional<Book> book = bookRepository.findById(id);
        bookRepository.deleteById(id);
        return book;
    }

    public void deleteAll() {
        bookRepository.deleteAll();
    }

    public List<Book> findBooksByAuthorsId(String id) {
        return bookRepository.findBooksByAuthorsId(id);
    }

    public List<Book> findBooksByCategoryId(String id) {
        return bookRepository.findBooksByCategoryId(id);
    }

    public void deleteBooksByCategoryId(String id) {
        bookRepository.deleteBooksByCategoryId(id);
    }
}
