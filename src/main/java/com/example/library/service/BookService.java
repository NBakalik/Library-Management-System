package com.example.library.service;

import com.example.library.entity.Book;
import com.example.library.repo.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBook() {
        return (List<Book>) bookRepository.findAll();
    }

    public Book getBook(Integer id) {
        return bookRepository.findById(id).orElse(null);
    }

    public Book updateBook(Book book) {
        bookRepository.save(book);
        return book;
    }

    public Book deleteBook(Integer id) {
        Book book = bookRepository.findById(id).orElse(null);
        bookRepository.deleteById(id);
        return book;
    }

}
