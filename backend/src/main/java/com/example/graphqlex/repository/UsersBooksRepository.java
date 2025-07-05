package com.example.graphqlex.repository;

import com.example.graphqlex.models.UsersBooks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UsersBooksRepository extends JpaRepository<UsersBooks, Long> {
    Optional<UsersBooks> findByBook_Id(Long id);

    List<UsersBooks> findByUser_id(Long userId);

    @Query(value = "Select distinct author_id from users_books ub where ub.user_id = :user", nativeQuery = true)
    List<Long> findAuthorsByUser_id(@Param("user") Long userId);
}
