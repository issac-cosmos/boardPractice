package com.example.board.Post.dtos;

import com.example.board.Author.domain.Author;
import com.example.board.Post.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostSaveReq {
    @NotEmpty
    private String title;
    private String contents;
    @NotEmpty
    private String email;

    public Post toEntity(Author author){
        return Post.builder().title(this.title).contents(this.contents).author(author).build();
    }
}
