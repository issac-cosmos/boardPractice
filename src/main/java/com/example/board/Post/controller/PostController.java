package com.example.board.Post.controller;

import com.example.board.Post.dtos.PostDetailRes;
import com.example.board.Post.dtos.PostListRes;
import com.example.board.Post.dtos.PostSaveReq;
import com.example.board.Post.dtos.PostUpdateReq;
import com.example.board.Post.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/create")
    public String postCreateScreen(){
        return "/post/post_create";
    }
    @PostMapping("/create")
    public String  postCreate(@Valid PostSaveReq dto){
        postService.save(dto);
        return "redirect:/post/list/paging";
    }
    @GetMapping("/list")
    public String  postList(Model model){
        List<PostListRes> postListResList = postService.findAll();
        model.addAttribute("postList",postListResList);
        return "/post/post_list";
    }
    @GetMapping("/list/paging")
    // 페이징처리를 위한 데이터형식 : localhost:8080/post/list/paging?size=10&page=0&sort=createdTime,desc
    public String  postListPaging
            (Model model ,@PageableDefault(size = 10,sort = "id",direction = Sort.Direction.DESC) Pageable pageable){
        Page<PostListRes> postListResPage =postService.findAllPaging(pageable);
        model.addAttribute("postList",postListResPage);
        return "/post/post_list";
    }

    @GetMapping("/list/fetchjoin")
    @ResponseBody
    public String postListFetchJoin(){
        postService.listFetchJoin();
        return "Ok";
    }

    @GetMapping("/detail/{id}")
    public String  postDetail(@PathVariable Long id , Model model){
        PostDetailRes postDetailRes = postService.findById(id);
        model.addAttribute("post",postDetailRes);
        return "/post/post_detail";
    }
    @PostMapping("/update/{id}")
    public String postUpdate(@PathVariable Long id, PostUpdateReq dto){
        postService.update(id, dto);
        return "OK";
    }
    @GetMapping("/delete/{id}")
    public String postDelete(@PathVariable Long id){
        postService.delete(id);
        return "OK";
    }
}
