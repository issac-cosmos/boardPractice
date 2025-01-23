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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
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
        Author author = authorRepository.findByEmail(dto.getEmail()).orElseThrow(()->new EntityNotFoundException());
        postRepository.save(dto.toEntity(author));
    }
    public List<PostListRes> findAll(){
        return postRepository.findAll().stream().map(p->p.postListFromEntity()).collect(Collectors.toList());

    }
    public Page<PostListRes> findAllPaging(Pageable pageable){
        Page<Post> pagePosts = postRepository.findAll(pageable);
        return pagePosts.map(p->p.postListFromEntity());
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
