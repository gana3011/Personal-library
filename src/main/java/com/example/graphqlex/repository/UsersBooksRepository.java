package com.example.graphqlex.repository;

import com.example.graphqlex.models.UsersBooks;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersBooksRepository extends JpaRepository<UsersBooks, Long> {
    Optional<UsersBooks> findByBook_Id(Long id);
}
