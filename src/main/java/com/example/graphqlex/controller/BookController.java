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
import com.example.graphqlex.service.BookService;
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
    private final BookService bookService;

    @QueryMapping
    public Book fetchBookById(@Argument Long id){
        return bookRepo.findById(id).orElseThrow(()->new RuntimeException("Book with id not found"));
    }

    @Transactional
    @MutationMapping
    public UsersBooks addBook(@Argument("input")BookDto bookDto){
        return bookService.addBook(bookDto);
    }

    @QueryMapping
    public List<Book> fetchBooksByAuthor(@Argument Long id){
        return bookRepo.findByAuthor_Id(id);
    }
}
