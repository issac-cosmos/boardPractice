package com.example.board.Post.service;

import com.example.board.Author.domain.Author;
import com.example.board.Author.repository.AuthorRepository;
import com.example.board.Post.domain.Post;
import com.example.board.Post.dtos.PostDetailRes;
import com.example.board.Post.dtos.PostListRes;
import com.example.board.Post.dtos.PostSaveReq;
import com.example.board.Post.dtos.PostUpdateReq;
import com.example.board.Post.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final AuthorRepository authorRepository;

    public PostService(PostRepository postRepository,AuthorRepository authorRepository) {
        this.postRepository = postRepository;
        this.authorRepository = authorRepository;
    }

    public void save(PostSaveReq dto){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Author author = authorRepository.findByEmail(email).orElseThrow(()->new EntityNotFoundException());
        LocalDateTime appointTime = null;
        if(dto.getAppointment().equals("Y") ){
            if(dto.getAppointmentTime().isEmpty() || dto.getAppointmentTime()==null){
                throw new IllegalArgumentException("시간을 입력해주세요");
            }else {
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
                appointTime = LocalDateTime.parse(dto.getAppointmentTime(), dateTimeFormatter);
                LocalDateTime now = LocalDateTime.now();
                if(appointTime.isBefore(now)){
                    throw new IllegalArgumentException("이전 시간입니다.");
                }
            }
        }
        postRepository.save(dto.toEntity(author,appointTime));
    }
    public List<PostListRes> findAll(){
        return postRepository.findAll().stream().map(p->p.postListFromEntity()).collect(Collectors.toList());

    }
    public Page<PostListRes> findAllPaging(Pageable pageable){
        Page<Post> pagePosts = postRepository.findAllByAppointment(pageable,"N");
        return pagePosts.map(p->p.postListFromEntity());
    }

    public List<PostListRes> listFetchJoin(){
//        일반 Join : author를 join해서 post 를 조회 하긴 하나 , author의 데이터는 실제 참조할때 쿼리가 N번 발생
//        List<Post> postList = postRepository.findAllJoin();
//        fetch join :author를 join해서 조회 하고. author의 데이터도 join시점에서 가져옴 쿼리 1번 발생
        List<Post> postList = postRepository.findAllFetchJoin();

        return postList.stream().map(p->p.postListFromEntity()).collect(Collectors.toList());
    }

    public PostDetailRes findById(Long id){
        Post post = postRepository.findById(id).orElseThrow(()->new EntityNotFoundException());
        return post.detailFromEntity();
        //return postRepository.findById(id).orElseThrow(()->new EntityNotFoundException()).detailFromEntity();
    }
    public void update(Long id, PostUpdateReq dto){
        Post post = postRepository.findById(id).orElseThrow(()->new EntityNotFoundException("not found"));
        post.updatePost(dto);
    }
    public void delete(Long id){
//        Post post = postRepository.findById(id).orElseThrow(()->new EntityNotFoundException());
//        postRepository.delete(post);
        postRepository.deleteById(id);
    }
}
