package com.example.graphqlex.service;

import com.example.graphqlex.dto.BookDto;
import com.example.graphqlex.models.Author;
import com.example.graphqlex.models.Book;
import com.example.graphqlex.models.User;
import com.example.graphqlex.models.UsersBooks;
import com.example.graphqlex.repository.AuthorRepository;
import com.example.graphqlex.repository.BookRepository;
import com.example.graphqlex.repository.UserRepository;
import com.example.graphqlex.repository.UsersBooksRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class BookServiceTests {
    @Mock AuthorRepository authorRepo;
    @Mock BookRepository bookRepo;
    @Mock UserRepository userRepo;
    @Mock UsersBooksRepository usersBooksRepo;
    @InjectMocks BookService bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddBook_UserExists_NewAuthorNewBookNewUsersBooks(){
        BookDto bookDto = new BookDto(1L,"Book 1","Author 1","Completed");
        Author newAuthor = new Author("Author 1");
        newAuthor.setId(1L);

        Book newBook = new Book("Book 1", newAuthor);
        newBook.setId(1L);

        User newUser = new User();
        newUser.setId(1L);
        newUser.setName("Test");
        newUser.setEmail("test@gmail.com");

        UsersBooks usersBooks = new UsersBooks(newUser, newBook, newAuthor, bookDto.getStatus());

        when(authorRepo.findByName("Author 1")).thenReturn(Optional.empty());
        when(authorRepo.save(any(Author.class))).thenReturn(newAuthor);

        when(userRepo.findById(1L)).thenReturn(Optional.of(newUser));

        when(bookRepo.findByName("Book 1")).thenReturn(Optional.empty());
        when(bookRepo.save(any(Book.class))).thenReturn(newBook);

        when(usersBooksRepo.findByBook_Id(1L)).thenReturn(Optional.empty());
        when(usersBooksRepo.save(any(UsersBooks.class))).thenReturn(usersBooks);

        UsersBooks result = bookService.addBook(bookDto);

        assertNotNull(result);
        assertEquals("Book 1",result.getBook().getName());
        assertEquals("Author 1",result.getAuthor().getName());
        assertEquals(1L,result.getUser().getId());
        assertEquals("Completed",result.getStatus());

        verify(authorRepo).save(any(Author.class));
        verify(bookRepo, times(2)).save(any(Book.class));
        verify(usersBooksRepo).save(any(UsersBooks.class));
    }

    @Test
    void testAddBook_UserNotFound_ThrowsException() {
        BookDto dto = new BookDto(2L,"Book 1", "Author 1","Completed");

        when(authorRepo.findByName("Author A")).thenReturn(Optional.of(new Author("Author A")));
        when(userRepo.findById(2L)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> bookService.addBook(dto));
    }

    @Test
    void testAddBook_UserExists_AuthorExists_NewBook(){

        BookDto bookDto = new BookDto(1L,"Book 1","Author 1","Completed");
        Author author = new Author("Author 1");
        author.setId(1L);

        Book newBook = new Book("Book 1", author);
        newBook.setId(1L);

        User newUser = new User();
        newUser.setId(1L);
        newUser.setName("Test");
        newUser.setEmail("test@gmail.com");

        UsersBooks usersBooks = new UsersBooks(newUser, newBook, author, bookDto.getStatus());
        when(authorRepo.findByName("Author 1")).thenReturn(Optional.of(author));
        when(userRepo.findById(1L)).thenReturn(Optional.of(newUser));
        when(bookRepo.findByName("Book 1")).thenReturn(Optional.empty());
        when(bookRepo.save(any(Book.class))).thenReturn(newBook);
        when(usersBooksRepo.findByBook_Id(1L)).thenReturn(Optional.empty());
        when(usersBooksRepo.save(any(UsersBooks.class))).thenReturn(usersBooks);

        UsersBooks result = bookService.addBook(bookDto);

        assertNotNull(result);
        assertEquals("Book 1",result.getBook().getName());
        assertEquals("Author 1",result.getAuthor().getName());
        assertEquals(1L,result.getUser().getId());
        assertEquals("Completed",result.getStatus());

        verify(authorRepo, never()).save(any(Author.class));
        verify(bookRepo, times(2)).save(any(Book.class));
        verify(usersBooksRepo).save(any(UsersBooks.class));
    }

    @Test
    void testAddBook_UserExists_AuthorExists_BookExists_NewUsersBooks(){
        Author author = new Author("Author 1");
        BookDto bookDto = new BookDto(1L,"Book 1","Author 1","Completed");
        author.setId(1L);

        Book book = new Book("Book 1", author);
        book.setId(1L);

        User newUser = new User();
        newUser.setId(1L);
        newUser.setName("Test");
        newUser.setEmail("test@gmail.com");

        UsersBooks usersBooks = new UsersBooks(newUser, book, author, bookDto.getStatus());
        when(authorRepo.findByName("Author 1")).thenReturn(Optional.of(author));
        when(userRepo.findById(1L)).thenReturn(Optional.of(newUser));
        when(bookRepo.findByName("Book 1")).thenReturn(Optional.of(book));
        when(usersBooksRepo.findByBook_Id(1L)).thenReturn(Optional.empty());
        when(usersBooksRepo.save(any(UsersBooks.class))).thenReturn(usersBooks);

        UsersBooks result = bookService.addBook(bookDto);

        assertNotNull(result);
        assertEquals("Book 1",result.getBook().getName());
        assertEquals("Author 1",result.getAuthor().getName());
        assertEquals(1L,result.getUser().getId());
        assertEquals("Completed",result.getStatus());

        verify(authorRepo, never()).save(any(Author.class));
        verify(bookRepo, times(1)).save(any(Book.class));
        verify(usersBooksRepo).save(any(UsersBooks.class));
    }

    @Test
    void testAddBooks_allExists(){
        Author author = new Author("Author 1");
        BookDto bookDto = new BookDto(1L,"Book 1","Author 1","Completed");
        author.setId(1L);

        Book book = new Book("Book 1", author);
        book.setId(1L);

        User newUser = new User();
        newUser.setId(1L);
        newUser.setName("Test");
        newUser.setEmail("test@gmail.com");

        UsersBooks usersBooks = new UsersBooks(newUser, book, author, "Reading");
        when(authorRepo.findByName("Author 1")).thenReturn(Optional.of(author));
        when(userRepo.findById(1L)).thenReturn(Optional.of(newUser));
        when(bookRepo.findByName("Book 1")).thenReturn(Optional.of(book));
        when(usersBooksRepo.findByBook_Id(1L)).thenReturn(Optional.of(usersBooks));


        UsersBooks result = bookService.addBook(bookDto);

        assertNotNull(result);
        assertEquals("Book 1",result.getBook().getName());
        assertEquals("Author 1",result.getAuthor().getName());
        assertEquals(1L,result.getUser().getId());
        assertEquals("Completed",result.getStatus());

        verify(authorRepo, never()).save(any(Author.class));
        verify(bookRepo, times(1)).save(any(Book.class));
        verify(usersBooksRepo, never()).save(any(UsersBooks.class));
    }
}
