package com.example.board.Post.domain;

import com.example.board.Author.domain.Author;
import com.example.board.Author.domain.Role;
import com.example.board.Common.domain.BaseTimeEntity;
import com.example.board.Post.dtos.PostDetailRes;
import com.example.board.Post.dtos.PostListRes;
import com.example.board.Post.dtos.PostUpdateReq;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Builder
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length=50, nullable = false)
    private String title;
    @Column(length=3000)
    private String  contents;
    private String appointment;
    private LocalDateTime appointmentTime;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Author author;

    public PostListRes postListFromEntity(){
        return PostListRes.builder()
                .id(this.id).title(this.title)
                .email(this.author.getEmail()).build();
    }
    public PostDetailRes detailFromEntity(){
        return PostDetailRes.builder()
                .id(this.id)
                .title(this.title)
                .contents(this.contents)
                .authorEmail(this.author.getEmail())
                .createdTime(this.getCreatedTime())
                .updatedTime(this.getUpdateTime())
                .build();
    }
    public void updatePost(PostUpdateReq dto){
        this.title =dto.getTitle();
        this.contents =dto.getContents();
    }
    public void updateAppointment(String appointment){
        this.appointment = appointment;
    }
}
