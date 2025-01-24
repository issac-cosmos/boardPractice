package com.example.board.Post.dtos;

import com.example.board.Author.domain.Author;
import com.example.board.Author.domain.Role;
import com.example.board.Post.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostSaveReq {
    @NotEmpty
    private String title;
    private String contents;
    @NotEmpty
    private String  appointment;
    private String  appointmentTime;

    public Post toEntity(Author author, LocalDateTime appointTime){
        return Post.builder()
                .title(this.title)
                .contents(this.contents)
                .author(author)
                .appointment(this.appointment)
                .appointmentTime(appointTime)
                .build();
    }
}