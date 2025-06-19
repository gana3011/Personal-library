package com.example.graphqlex.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users_books",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "book_id"})
        })
public class UsersBooks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    private String status;

    public UsersBooks(User user, Book book, Author author, String status){
        this.user = user;
        this.book =  book;
        this.author = author;
        this.status = status;
    }
}
