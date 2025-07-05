package com.example.graphqlex.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="author_id")
    private Long id;
    private String name;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<Book> books = new HashSet<>();

//    @ManyToMany(mappedBy = "authors", fetch = FetchType.LAZY)
//    private Set<User> users = new HashSet<>();

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<UsersBooks> usersBooks = new HashSet<>();

    public Author(String name){
        this.name = name;
    }

    public void addBook(Book book){
        this.books.add(book);
        book.setAuthor(this);
    }

//    public void addUser(User user) {
//        this.users.add(user);
//        if (!user.getAuthors().contains(this)) {
//            user.getAuthors().add(this);
//        }
//    }
}
