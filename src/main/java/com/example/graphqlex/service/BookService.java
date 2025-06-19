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
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BookService {
    AuthorRepository authorRepo;
    BookRepository bookRepo;
    UserRepository userRepo;
    UsersBooksRepository usersBooksRepo;

    public UsersBooks addBook(BookDto bookDto){
        Author author = authorRepo.findByName(bookDto.getAuthor()).orElseGet(()->{
            Author newAuthor = new Author(bookDto.getAuthor());
            return authorRepo.save(newAuthor);
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
        entry.setStatus(bookDto.getStatus());
//        author.addUser(user);
//        user.addAuthor(author);
        user.addBookWithStatus(entry, book, author);
//        System.out.println(book.getName());
//        System.out.println(entry.getBook().getName());
        bookRepo.save(book);
        return entry;
    }
}
