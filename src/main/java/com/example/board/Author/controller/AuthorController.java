package com.example.board.Author.controller;

import com.example.board.Author.dtos.AuthorDetailRes;
import com.example.board.Author.dtos.AuthorListRes;
import com.example.board.Author.dtos.AuthorSaveReq;
import com.example.board.Author.dtos.AuthorUpdateReq;
import com.example.board.Author.service.AuthorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/author")
public class AuthorController {

    private final AuthorService authorService;
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }
    @GetMapping("/create")
    public String authorCreateScreen(){
        return "/author/author_create";
    }
    @GetMapping("/login")
    public String authorLoginScreen(){
        return "/author/author_login";
    }
    @PostMapping("/create")
    public String  authorCreate(@Valid AuthorSaveReq dto){
        authorService.save(dto);
        return "redirect:/";
    }
    @GetMapping("/list")
    public String authorList(Model model){
        List<AuthorListRes> authorListResList = authorService.findAll();
        model.addAttribute("authorList", authorListResList);
        return "/author/author_list";
    }

    @GetMapping("/detail/{id}")
    public String  authorDetail(@PathVariable Long id, Model model){
        AuthorDetailRes authorDetailRes = authorService.findById(id);
        model.addAttribute("author",authorDetailRes);
        return "/author/author_detail";
    }
    @GetMapping("/delete/{id}")
    public String authorDelete(@PathVariable Long id){
        authorService.delete(id);
        return "OK";
    }
    @PostMapping("/update/{id}")
    public String authorUpdate(@PathVariable Long id, AuthorUpdateReq dto){
        authorService.update(id,dto);
        return "OK";
    }
}
