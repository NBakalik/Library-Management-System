package com.example.library.repo;

import com.example.library.entity.Author;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Integer> {

    List<Author> findAuthorsByBooksId(int id);
}
