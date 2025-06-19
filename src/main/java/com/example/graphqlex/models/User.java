package com.example.graphqlex.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.transaction.reactive.GenericReactiveTransaction;

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
//    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
//    @JoinTable(
//            name = "user_authors",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "author_id")
//    )
//    private Set<Author> authors = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<UsersBooks> usersBooks = new HashSet<>();

//    public void addAuthor(Author author){
//        this.authors.add(author);
//        if(!author.getUsers().contains(this)){
//            author.getUsers().add(this);
//        }
//    }

    public UsersBooks addBookWithStatus(UsersBooks newEntry, Book book, Author author){
//        for(UsersBooks entry: this.usersBooks){
//            if(entry.getBook().getName().equals(book.getName())) {
//                entry.setStatus(status);
//                return entry;
//            }
//        }


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
