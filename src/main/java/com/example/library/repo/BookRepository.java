package com.example.library.repo;

import com.example.library.entity.Book;
import com.example.library.entity.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends CrudRepository<Book, Integer> {
    List<Book> findBooksByCategory(Category category);

    List<Book> findBooksByAuthorsId(int id);
}
