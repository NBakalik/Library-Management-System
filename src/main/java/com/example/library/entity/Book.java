package com.example.library.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    private String name;
    @ManyToOne
    @JoinColumn(name = "id")
    private Author author;
}
