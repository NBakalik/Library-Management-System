package com.example.library.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Getter
@Setter
@ToString
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NonNull
    @Column(name = "name")
    private String name;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToMany(mappedBy = "books")
    private List<Author> authors;
}
