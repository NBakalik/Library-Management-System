package com.example.library.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Document
public class Author {
    @Id
    private String id;
    private String name;
    private Date birthDate;
    private String country;
    @DBRef
    private List<Book> books = new ArrayList<>();

    public void addBook(Book book) {
        this.books.add(book);
        book.getAuthors().add(this);
    }

    public void removeBook(String bookId) {
        Book book = this.books.stream().filter(b -> b.getId().equals(bookId)).findFirst().orElse(null);
        if (book != null) {
            this.books.remove(book);
            book.getAuthors().remove(this);
        }
    }
}
