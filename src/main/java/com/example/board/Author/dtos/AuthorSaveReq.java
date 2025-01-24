package com.example.board.Author.dtos;

import com.example.board.Author.domain.Author;
import com.example.board.Author.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthorSaveReq {
    @NotEmpty
    private String name;
    @NotEmpty
    private String email;
    @NotEmpty
    @Size(min=8)
    private String password;
    //사용자가 String 으로 입력해도 Role클래스로 자동 변환
    //ex) ADMIN, USER 등으로 입력시 Enum클래스로 변환
    private Role role = Role.USER;

    public Author toEntity(String encodedPassword){
        return Author.builder().name(this.name).email(this.email).password(encodedPassword).role(this.role).build();
    }
}
