package com.example.library.repo;

import com.example.library.entity.Author;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends MongoRepository<Author, String> {

    List<Author> findAuthorsByBooksId(String id);
}
