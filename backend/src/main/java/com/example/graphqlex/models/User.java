package com.example.graphqlex.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    private String name;
    private String email;
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<UsersBooks> usersBooks = new HashSet<>();

    public UsersBooks addBookWithStatus(UsersBooks newEntry, Book book, Author author){

        this.usersBooks.add(newEntry);
        if (book.getUsersBooks() != null && !book.getUsersBooks().contains(newEntry)) {
            book.getUsersBooks().add(newEntry);
        }
        if (author.getUsersBooks() != null && !author.getUsersBooks().contains(newEntry)) {
            author.getUsersBooks().add(newEntry);
        }
        return newEntry;
    }

}
