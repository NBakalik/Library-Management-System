package com.example.library.service;

import com.example.library.entity.Author;
import com.example.library.repo.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author addAuthor(Author author) {
        return authorRepository.save(author);
    }

    public List<Author> getAllAuthor() {
        return authorRepository.findAll();
    }

    public Optional<Author> getAuthor(String id) {
        return authorRepository.findById(id);
    }

    public Author updateAuthor(Author author) {
        return authorRepository.save(author);
    }

    public Optional<Author> deleteAuthor(String id) {
        Optional<Author> author = authorRepository.findById(id);
        authorRepository.deleteById(id);
        return author;
    }

    public void deleteAll() {
        authorRepository.deleteAll();
    }

    public List<Author> findAuthorsByBooksId(String id) {
        return authorRepository.findAuthorsByBooksId(id);
    }
}
