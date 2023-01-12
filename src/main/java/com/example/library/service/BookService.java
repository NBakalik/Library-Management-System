package com.example.library.service;

import com.example.library.entity.Book;
import com.example.library.repo.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    public List<Book> getAllBook() {
        return (List<Book>) bookRepository.findAll();
    }

    public Optional<Book> getBook(Integer id) {
        return bookRepository.findById(id);
    }

    public Book updateBook(Book book) {
        return bookRepository.save(book);
    }

    public Optional<Book> deleteBook(Integer id) {
        Optional<Book> book = bookRepository.findById(id);
        bookRepository.deleteById(id);
        return book;
    }

    public void deleteAll() {
        bookRepository.deleteAll();
    }

    public List<Book> findBooksByAuthorsId(int id){
        return bookRepository.findBooksByAuthorsId(id);
    }
}
