package com.example.board.Author.service;

import com.example.board.Author.domain.Author;
import com.example.board.Author.dtos.AuthorDetailRes;
import com.example.board.Author.dtos.AuthorListRes;
import com.example.board.Author.dtos.AuthorSaveReq;
import com.example.board.Author.dtos.AuthorUpdateReq;
import com.example.board.Author.repository.AuthorRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthorService(AuthorRepository authorRepository, PasswordEncoder passwordEncoder) {
        this.authorRepository = authorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void save(AuthorSaveReq dto){
        if(authorRepository.findByEmail(dto.getEmail()).isPresent()){
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        Author author = authorRepository.save(dto.toEntity(passwordEncoder.encode(dto.getPassword())));
////        cascade를 활용하지 않고, 별도로 post 데이터 만드는 경우
//        postRepository.save(Post.builder()
//                .title("반갑습니다.")
//                .contents("처음뵙겠습니다.")
//                .author(author)
//                .build());

//        cascade를 활용해서, post데이터를 함께 만드는 경우
//        Author author = Author.builder()
//                .name(dto.getName())
//                .email(dto.getEmail())
//                .role(dto.getRole())
//                .password(dto.getPassword())
//                .build();
////        post를 생성하는 시점에 author가 아직 DB에 저장되지 않은것으로 보이나 jpa가 author와 post를 save하는 시점에 선후관계를 맞춰줌
//        author.getPosts().add(Post.builder().title("안녕하세요1 반갑습니다").author(author).build());
//        author.getPosts().add(Post.builder().title("안녕하세요2 반갑습니다").author(author).build());
//        authorRepository.save(author);

    }
    public List<AuthorListRes> findAll(){
        return authorRepository.findAll()
                .stream().map(a->a.listDtoFromEntity()).collect(Collectors.toList());
    }

    public AuthorDetailRes findById(Long id){
        return authorRepository.findById(id).orElseThrow(()->new EntityNotFoundException("not found")).detailFromEntity();
    }
    public void delete(Long id){
        Author author = authorRepository.findById(id).orElseThrow(()->new EntityNotFoundException("not found"));
        authorRepository.delete(author);
    }
    public void update(Long id , AuthorUpdateReq dto){
        Author author = authorRepository.findById(id).orElseThrow(()->new EntityNotFoundException("not found"));
        author.updateProfile(dto);
        // 기존객체에 변경이 발생할경우, 별도의 save없이도 jpa가 엔티티의 변경을 자동인지하고, 변경사항을 DB반영
        // 이를 dirtychecking이라 부르고, 반드시 transactional어노테이션이 있을경우 작동.
        // authorRepository.save(author);
    }
}
