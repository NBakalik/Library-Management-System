package com.example.library.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Document
public class Book {
    @Id
    private String id;
    private String name;
    @DBRef
    private Category category;
    @DBRef(lazy = true)
    @JsonIgnore
    private List<Author> authors = new ArrayList<>();
}
