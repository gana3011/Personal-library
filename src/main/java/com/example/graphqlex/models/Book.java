package com.example.graphqlex.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="book_id")
    private Long id;
    private String name;
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "author_id", nullable = false)
    Author author;
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "user_id", nullable = false)
    User user;
    private String status;

    public Book(User user, String name, Author author, String status){
        this.user = user;
        this.name=name;
        this.author=author;
        this.status=status;
    }

}



