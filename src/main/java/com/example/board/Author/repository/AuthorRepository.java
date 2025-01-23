package com.example.board.Author.repository;

import com.example.board.Author.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author ,Long> {
    Optional<Author> findByEmail(String email);
}
