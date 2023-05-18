package com.example.library.service;

import com.example.library.entity.Author;
import com.example.library.entity.Book;
import com.example.library.exeptions.AuthorAlreadyExistException;
import com.example.library.exeptions.AuthorNotFoundException;
import com.example.library.exeptions.BookNotFoundException;
import com.example.library.repo.AuthorRepository;
import com.example.library.repo.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository,
                         BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    public Author addAuthor(Author author) {
        if (author.getId() != null) {
            throw new AuthorAlreadyExistException("Author with id:" + author.getId() + " already exists");
        }
        return authorRepository.save(author);
    }

    public List<Author> getAllAuthor() {
        List<Author> authorList = (List<Author>) authorRepository.findAll();
        if (authorList.isEmpty()) {
            throw new AuthorNotFoundException("No authors found");
        }
        return authorList;
    }

    public Author getAuthor(Integer id) {
        Optional<Author> author = authorRepository.findById(id);
        if (author.isEmpty()) {
            throw new AuthorNotFoundException("No author with id: " + id);
        }
        return author.get();
    }

    @Transactional
    public Author updateAuthor(int id, Author newAuthor) {
        Optional<Author> author = authorRepository.findById(id);
        if (author.isEmpty()) {
            throw new AuthorNotFoundException("No author found with id: " + id);
        }
        author.get().setName(newAuthor.getName());
        author.get().setBirthDate(newAuthor.getBirthDate());
        author.get().setCountry(newAuthor.getCountry());
        author.get().setBooks(newAuthor.getBooks());
        return newAuthor;
    }

    @Transactional
    public Author deleteAuthor(Integer id) {
        Optional<Author> author = authorRepository.findById(id);
        if (author.isEmpty()) {
            throw new AuthorNotFoundException("No author found with id: " + id);
        }
        authorRepository.deleteById(id);
        return author.get();
    }

    public void deleteAll() {
        authorRepository.deleteAll();
    }

    @Transactional
    public void deleteBookFromAuthor(int authorId, int bookId){
        Optional<Author> author = authorRepository.findById(authorId);
        if (author.isEmpty()) {
            throw new AuthorNotFoundException("No author found with id: " + authorId);
        }
        author.get().removeBook(bookId);
    }

    @Transactional
    public List<Author> findAuthorsByBooksId(int id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isEmpty()) {
            throw new BookNotFoundException("No book with id:" + id);
        }
        return authorRepository.findAuthorsByBooksId(id);
    }
}
