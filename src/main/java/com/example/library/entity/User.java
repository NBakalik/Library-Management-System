package com.example.library.entity;

import lombok.*;
import javax.persistence.*;

@NoArgsConstructor
@Entity
@Getter
@Setter
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
}
