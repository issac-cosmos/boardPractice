package com.example.board.author;

import com.example.board.Author.domain.Author;
import com.example.board.Author.domain.Role;
import com.example.board.Author.dtos.AuthorSaveReq;
import com.example.board.Author.repository.AuthorRepository;
import com.example.board.Author.service.AuthorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@Transactional
public class AuthorServiceTest {
    @Autowired
    private AuthorService authorService;
    @Autowired
    private AuthorRepository authorRepository;

    @Test
    public void authorSaveTest(){
        AuthorSaveReq authorSaveReq = new AuthorSaveReq(
                "kyung"
                , "kyung@naver.com"
                ,"1234"
                , Role.USER);
        authorService.save(authorSaveReq);

        Author author = authorRepository.findByEmail("kyung@naver.com").orElse(null);
        Assertions.assertEquals(authorSaveReq.getEmail(),author.getEmail());
    }
    @Test
    public void findAllTest(){
        int beforeSize = authorService.findAll().size();
        authorRepository.save(Author.builder().name("a1").email("a1@aaa.com").password("11").build());
        authorRepository.save(Author.builder().name("a2").email("a2@aaa.com").password("22").build());
        authorRepository.save(Author.builder().name("a3").email("a3@aaa.com").password("33").build());

        int afterSize = authorService.findAll().size();

        Assertions.assertEquals(beforeSize+3,afterSize);
    }
}
