package com.example.library.service;

import com.example.library.entity.Author;
import com.example.library.exeptions.AuthorAlreadyExistException;
import com.example.library.exeptions.AuthorNotFoundException;
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

    public Author updateAuthor(int id, Author newAuthor) {
        Optional<Author> author = authorRepository.findById(id);
        if (author.isEmpty()) {
            throw new AuthorNotFoundException("No author found with id: " + id);
        }
        newAuthor.setId(id);
        return authorRepository.save(newAuthor);
    }

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

    public List<Author> findAuthorsByBooksId(int id) {
        return authorRepository.findAuthorsByBooksId(id);
    }
}
