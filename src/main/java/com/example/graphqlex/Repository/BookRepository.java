package com.example.graphqlex.Repository;

import com.example.graphqlex.Model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
    Optional<Book> findByName(String name);
    List<Book> findByAuthor_Id(Long id);
}
