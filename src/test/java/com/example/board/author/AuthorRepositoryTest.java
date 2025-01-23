package com.example.board.author;

import com.example.board.Author.domain.Author;
import com.example.board.Author.domain.Role;
import com.example.board.Author.repository.AuthorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional //테스트 완료 후 데이터가 실제 insert되지 않고, 롤백
public class AuthorRepositoryTest {
    @Autowired
    private AuthorRepository authorRepository;

    @Test
    public void authorSaveTest(){
//        테스트 검증로직 : 객체 생성 save -> 재 조회 -> 객체와 조회한 객체가 같은지를 비교
//        준비(prepare, given)
        Author author = Author.builder()
                .name("ABC")
                .email("ABC@naver.com")
                .password("1234")
                .role(Role.USER)
                .build();
//        실행(execute, when)
        authorRepository.save(author);
//        검증(then)
        Author authorDB = authorRepository.findByEmail("ABC@naver.com").orElse(null);
        Assertions.assertEquals(author.getEmail(),authorDB.getEmail());
    }
}
