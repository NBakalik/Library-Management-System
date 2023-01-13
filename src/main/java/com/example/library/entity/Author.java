package com.example.library.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Entity
@Getter
@Setter
@ToString
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "birth_date")
    private Date birthDate;

    @Column(name = "country")
    private String country;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "author_has_book",
            joinColumns = {@JoinColumn(name = "author_id")},
            inverseJoinColumns = {@JoinColumn(name = "book_id")})
    private List<Book> books = new ArrayList<>();

    public void addBook(Book book) {
        this.books.add(book);
        book.getAuthors().add(this);
    }

    public void removeBook(int bookId) {
        Book book = this.books.stream().filter(b -> b.getId() == bookId).findFirst().orElse(null);
        if (book != null) {
            this.books.remove(book);
            book.getAuthors().remove(this);
        }
    }
}
