package com.example.library.service;

import com.example.library.entity.Author;
import com.example.library.repo.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> getAllAuthor() {
        return (List<Author>) authorRepository.findAll();
    }

    public Author getAuthor(Integer id) {
        return authorRepository.findById(id).orElse(null);
    }

    public Author updateAuthor(Author author) {
        authorRepository.save(author);
        return author;
    }

    public Author deleteAuthor(Integer id) {
        Author author = authorRepository.findById(id).orElse(null);
        authorRepository.deleteById(id);
        return author;
    }
}
