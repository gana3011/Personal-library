package com.example.graphqlex.controller;

import com.example.graphqlex.dto.BookDto;
import com.example.graphqlex.models.Author;
import com.example.graphqlex.models.Book;
import com.example.graphqlex.models.User;
import com.example.graphqlex.repository.AuthorRepository;
import com.example.graphqlex.repository.BookRepository;
import com.example.graphqlex.repository.UserRepository;
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

    @QueryMapping
    public List<Book> fetchBooks(){
        return bookRepo.findAll();
    }

    @QueryMapping
    public Book fetchBookById(@Argument Long id){
        return bookRepo.findById(id).orElseThrow(()->new RuntimeException("Book with id not found"));
    }

    @MutationMapping
    public Book addBook(@Argument("input")BookDto bookDto){
        Author authorObj = authRepo.findByName(bookDto.getAuthor()).orElseGet(()->{
            Author newAuthor = new Author(bookDto.getAuthor());
            return authRepo.save(newAuthor);
        });
        User user = userRepo.findById(bookDto.getUserid()).orElseThrow(()->new UsernameNotFoundException("User not found"));
        Book book = bookRepo.findByName(bookDto.getName()).orElseGet(()->{
            Book newBook = new Book(user,bookDto.getName(),authorObj, bookDto.getStatus());
            return bookRepo.save(newBook);
        });
        authorObj.addBook(book);
        return bookRepo.save(book);
    }

    @QueryMapping
    public List<Book> fetchBooksByAuthor(@Argument Long id){
        return bookRepo.findByAuthor_Id(id);
    }

}
