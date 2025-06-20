package com.example.graphqlex.controller;

import com.example.graphqlex.dto.BookDto;
import com.example.graphqlex.models.Book;
import com.example.graphqlex.models.UsersBooks;
import com.example.graphqlex.repository.BookRepository;
import com.example.graphqlex.service.BookService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;


@Controller
@AllArgsConstructor
public class BookController {
    private final BookRepository bookRepo;
    private final BookService bookService;

    @QueryMapping
    @PreAuthorize("isAuthenticated()")
    public Book fetchBooksById(@Argument Long id){
        return bookRepo.findById(id).orElseThrow(()->new RuntimeException("Book with id not found"));
    }

    @Transactional
    @MutationMapping
    @PreAuthorize("isAuthenticated()")
    public UsersBooks addBook(@Argument("input")BookDto bookDto){
        return bookService.addBook(bookDto);
    }

    @QueryMapping
    public List<Book> fetchBooksByAuthor(@Argument Long id){
        return bookRepo.findByAuthor_Id(id);
    }

    @QueryMapping
    @PreAuthorize("isAuthenticated()")
    public List<UsersBooks> fetchBooksByUserId(@Argument Long userId){
        return bookService.fetchBooksByUserId(userId);
    }

}
