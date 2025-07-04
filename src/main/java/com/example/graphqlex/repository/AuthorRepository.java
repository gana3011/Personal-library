package com.example.graphqlex.repository;

import com.example.graphqlex.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author,Long> {
    Optional<Author> findByName(String name);
}
