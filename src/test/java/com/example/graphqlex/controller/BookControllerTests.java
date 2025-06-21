package com.example.graphqlex.controller;

import com.example.graphqlex.dto.BookDto;
import com.example.graphqlex.models.Author;
import com.example.graphqlex.models.Book;
import com.example.graphqlex.models.User;
import com.example.graphqlex.models.UsersBooks;
import com.example.graphqlex.repository.BookRepository;
import com.example.graphqlex.service.BookService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import org.springframework.graphql.test.tester.GraphQlTester;

import java.util.List;

@GraphQlTest(BookController.class)
public class BookControllerTests {
    @Autowired
    private GraphQlTester graphQlTester;

    @MockitoBean
    private BookService bookService;

    @MockitoBean
    private BookRepository bookRepo;

    @Test
    void testAddBook(){
        BookDto input = new BookDto(1L,"Book 1", "Author 1", "Completed");
        User user = new User();
        user.setId(input.getUserid());
        Author author = new Author("Author 1");
        author.setId(1L);
        Book book = new Book("Book 1", author);
        book.setId(1L);
        UsersBooks usersBooks = new UsersBooks(user,book,author,input.getStatus());
        usersBooks.setId(1L);
        when(bookService.addBook(any(BookDto.class))).thenReturn(usersBooks);

        String mutation = """
                mutation{
                addBook(input:{
                userId:1,
                name: "Book 1",
                author: "Author 1",
                status: "Completed"
                }){
                id,
                author{
                name
                },
                status
               }
               }
               """;

        graphQlTester.document(mutation)
                .execute()
                .path("addBook.id").entity(Long.class).isEqualTo(1L)
                .path("addBook.author.name").entity(String.class).isEqualTo("Author 1")
                .path("addBook.status").entity(String.class).isEqualTo("Completed");

//        graphQlTester.document(mutation)
//                .execute()
//                .path("addBook") // ← just to see what’s returned
//                .entity(Object.class)
//                .satisfies(response -> {
//                    System.out.println(response);
//                });

        verify(bookService).addBook(any(BookDto.class));
    }

    @Test
    void testFetchBooksByUserId(){
        User user = new User();
        user.setId(1L);
        Author author = new Author("Author 1");
        author.setId(1L);
        Book book = new Book("Book 1", author);
        book.setId(1L);
        UsersBooks usersBooks = new UsersBooks(user,book,author,"Completed");
        usersBooks.setId(1L);

        when(bookService.fetchBooksByUserId(1L)).thenReturn(List.of(usersBooks));

        String query = """
                query{
                fetchBooksByUserId(userId:1){
                book{
                      name
                    },
                author{
                      name
                    },
                status
                }
                }
                """;


        graphQlTester.document(query)
                .execute()
                .path("fetchBooksByUserId[0].book.name").entity(String.class).isEqualTo("Book 1")
                .path("fetchBooksByUserId[0].author.name").entity(String.class).isEqualTo("Author 1")
                .path("fetchBooksByUserId[0].status").entity(String.class).isEqualTo("Completed");

//        graphQlTester.document(query)
//                .execute()
//                .path("fetchBooksByUserId") // ← just to see what’s returned
//                .entity(Object.class)
//                .satisfies(response -> {
//                    System.out.println(response);
//                });

        verify(bookService).fetchBooksByUserId(1L);
    }
}
