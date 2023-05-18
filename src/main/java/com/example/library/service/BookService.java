package com.example.library.service;

import com.example.library.entity.Author;
import com.example.library.entity.Book;
import com.example.library.entity.Category;
import com.example.library.exeptions.AuthorNotFoundException;
import com.example.library.exeptions.BookNotFoundException;
import com.example.library.exeptions.CategoryNotFoundException;
import com.example.library.repo.AuthorRepository;
import com.example.library.repo.BookRepository;
import com.example.library.repo.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    @Transactional
    public Book addBookToCategory(int categoryId, Book newBook) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.isEmpty()) {
            throw new CategoryNotFoundException("No category with id: " + categoryId);
        }
        category.get().addBook(newBook);
        return newBook;
    }

    @Transactional
    public Book addBookToAuthor(int authorId, Book newBook) {
        Optional<Author> author = authorRepository.findById(authorId);
        if (author.isEmpty()) {
            throw new AuthorNotFoundException("No author with id: " + authorId);
        }
        if (newBook.getId() == null) {
            author.get().addBook(newBook);
            return newBook;
        } else {
            Optional<Book> book = bookRepository.findById(newBook.getId());
            if (book.isEmpty()) {
                throw new BookNotFoundException("No book with id: " + newBook.getId());
            }
            author.get().addBook(book.get());
            return book.get();
        }
    }

    public List<Book> getAllBook() {
        List<Book> bookList = (List<Book>) bookRepository.findAll();
        if (bookList.isEmpty()) {
            throw new BookNotFoundException("No books found");
        }
        return bookList;
    }

    public Book getBook(Integer id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isEmpty()) {
            throw new BookNotFoundException("No book with id: " + id);
        }
        return book.get();
    }

    @Transactional
    public Book updateBook(int id, Book newBook) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isEmpty()) {
            throw new BookNotFoundException("No book found with id: " + id);
        }
        book.get().setName(newBook.getName());
        book.get().setCategory(newBook.getCategory());
        return book.get();
    }

    @Transactional
    public Book deleteBook(Integer id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isEmpty()) {
            throw new BookNotFoundException("No book found with id: " + id);
        }
        bookRepository.deleteById(id);
        return book.get();
    }

    public void deleteAll() {
        bookRepository.deleteAll();
    }

    public List<Book> findBooksByAuthorsId(int id) {
        Optional<Author> author = authorRepository.findById(id);
        if (author.isEmpty()) {
            throw new AuthorNotFoundException("No author with id: " + id);
        }
        return bookRepository.findBooksByAuthorsId(id);
    }

    public List<Book> findBooksByCategoryId(int id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isEmpty()) {
            throw new CategoryNotFoundException("No category with id: " + id);
        }
        return bookRepository.findBooksByCategoryId(id);
    }

    @Transactional
    public void deleteBooksByCategoryId(int id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isEmpty()) {
            throw new CategoryNotFoundException("No category with id: " + id);
        }
        bookRepository.deleteBooksByCategoryId(id);
    }
}
