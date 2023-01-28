package com.example.library.repo;

import com.example.library.entity.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends MongoRepository<Book, String> {
    List<Book> findBooksByAuthorsId(String id);

    List<Book> findBooksByCategoryId(String id);

    void deleteBooksByCategoryId(String id);
}
