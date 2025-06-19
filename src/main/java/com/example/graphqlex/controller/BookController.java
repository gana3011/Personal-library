package com.example.graphqlex.controller;

import com.example.graphqlex.dto.BookDto;
import com.example.graphqlex.models.Author;
import com.example.graphqlex.models.Book;
import com.example.graphqlex.models.User;
import com.example.graphqlex.models.UsersBooks;
import com.example.graphqlex.repository.AuthorRepository;
import com.example.graphqlex.repository.BookRepository;
import com.example.graphqlex.repository.UserRepository;
import com.example.graphqlex.repository.UsersBooksRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;

import java.util.List;


@Controller
@AllArgsConstructor
public class BookController {
    private final BookRepository bookRepo;
    private final AuthorRepository authRepo;
    private final UserRepository userRepo;
    private final UsersBooksRepository usersBooksRepo;


    @QueryMapping
    public List<Book> fetchBooks(){
        return bookRepo.findAll();
    }

    @QueryMapping
    public Book fetchBookById(@Argument Long id){
        return bookRepo.findById(id).orElseThrow(()->new RuntimeException("Book with id not found"));
    }

    @Transactional
    @MutationMapping
    public UsersBooks addBook(@Argument("input")BookDto bookDto){
        Author author = authRepo.findByName(bookDto.getAuthor()).orElseGet(()->{
            Author newAuthor = new Author(bookDto.getAuthor());
            return authRepo.save(newAuthor);
        });
        User user = userRepo.findById(bookDto.getUserid()).orElseThrow(()->new UsernameNotFoundException("User not found"));
        Book book = bookRepo.findByName(bookDto.getName()).orElseGet(()->{
            Book newBook = new Book(bookDto.getName(),author);
            return bookRepo.save(newBook);
        });
        author.addBook(book);
        UsersBooks entry = usersBooksRepo.findByBook_Id(book.getId()).orElseGet(()->{
            UsersBooks ub = new UsersBooks(user, book, author, bookDto.getStatus());
            return usersBooksRepo.save(ub);
        });

//        author.addUser(user);
//        user.addAuthor(author);
         user.addBookWithStatus(entry, book, author);
//        System.out.println(book.getName());
//        System.out.println(entry.getBook().getName());
        bookRepo.save(book);
        return entry;
    }

    @QueryMapping
    public List<Book> fetchBooksByAuthor(@Argument Long id){
        return bookRepo.findByAuthor_Id(id);
    }

}
