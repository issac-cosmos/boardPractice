package com.example.board.Post.repository;

import com.example.board.Post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    //findByAuthor(Author author);
    Page<Post> findAll(Pageable pageable);
}
