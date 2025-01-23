package com.example.board.Author.dtos;

import com.example.board.Author.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AuthorDetailRes {
    private Long id;
    private String  name;
    private String  email;
    private String  password;
    private Role role;
    private int postCount;
    private LocalDateTime createdTime;
}
