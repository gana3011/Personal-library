package com.example.graphqlex.Model;

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
    @JoinColumn(name = "author_id", nullable = false)
    Author author;
    private String status;

    public Book(String name, Author author, String status){
        this.name=name;
        this.author=author;
        this.status=status;
    }

}



