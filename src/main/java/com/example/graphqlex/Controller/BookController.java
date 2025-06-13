package com.example.graphqlex.Controller;

import com.example.graphqlex.Model.Author;
import com.example.graphqlex.Model.Book;
import com.example.graphqlex.Repository.AuthorRepository;
import com.example.graphqlex.Repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;


@Controller
@AllArgsConstructor
public class BookController {
    private final BookRepository bookRepo;
    private final AuthorRepository authRepo;

    @QueryMapping
    public List<Book> fetchBooks(){
        return bookRepo.findAll();
    }

    @QueryMapping
    public Book fetchBookById(@Argument Long id){
        return bookRepo.findById(id).orElseThrow(()->new RuntimeException("Book with id not found"));
    }

    @MutationMapping
    public Book addBook(@Argument String name, @Argument String author, @Argument String status){
        Author authorObj = authRepo.findByName(author).orElseGet(()->{
            Author newAuthor = new Author(author);
            return authRepo.save(newAuthor);
        });
        Book book = bookRepo.findByName(name).orElseGet(()->{
            Book newBook = new Book(name,authorObj,status);
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
