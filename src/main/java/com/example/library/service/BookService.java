package com.example.library.service;

import com.example.library.entity.Author;
import com.example.library.entity.Book;
import com.example.library.exeptions.BookNotFoundException;
import com.example.library.repo.AuthorRepository;
import com.example.library.repo.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    public BookService(BookRepository bookRepository,
                       AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    public Book addBook(Author author, Book newBook) {
        Optional<Integer> bookId = Optional.ofNullable(newBook.getId());
        if (bookId.isPresent()) {
            Book book = bookRepository.findById(bookId.get()).orElseThrow(BookNotFoundException::new);
            author.addBook(book);
            authorRepository.save(author);
            return book;
        }

        author.addBook(newBook);
        return bookRepository.save(newBook);
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

    public List<Book> findBooksByAuthorsId(int id) {
        return bookRepository.findBooksByAuthorsId(id);
    }

    public List<Book> findBooksByCategoryId(int id) {
        return bookRepository.findBooksByCategoryId(id);
    }

    public void deleteBooksByCategoryId(int id) {
        bookRepository.deleteBooksByCategoryId(id);
    }
}
