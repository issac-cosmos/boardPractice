package com.example.board.Author.domain;

import com.example.board.Author.dtos.AuthorDetailRes;
import com.example.board.Author.dtos.AuthorListRes;
import com.example.board.Author.dtos.AuthorUpdateReq;
import com.example.board.Common.domain.BaseTimeEntity;
import com.example.board.Post.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
@AllArgsConstructor
@Builder
public class Author extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 20, nullable = false)
    private String name;
    @Column(length = 30, nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
//    enum은 기본적으로 숫자값으로 db에 들어가기때문에 별도로 STRING지정 필요
    @Enumerated(EnumType.STRING)
    private Role role;
    // OneToMany의 기본값이 fetch lazy라 별도의 설정은 없음
    // mappedBy에 ManyToOne쪽에 변수명을 문자열로 지정
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    // 빌더패턴에서 변수 초기화 (디폴트 값)시 Buider.Default 어노테이션 사용
    @Builder.Default
    private List<Post> posts = new ArrayList<>();

    public void updateProfile(AuthorUpdateReq dto){
        this.name = dto.getName();
        this.password = dto.getPassword();
    }

    public AuthorListRes listDtoFromEntity(){
        return AuthorListRes.builder().id(this.id).email(this.email).name(this.name).build();
    }

    public AuthorDetailRes detailFromEntity(){
        return AuthorDetailRes.builder()
                .id(this.id)
                .name(this.name)
                .email(this.email)
                .password(this.password)
                .role(this.role)
                .postCount(this.posts.size())
                .createdTime(this.getCreatedTime())
                .build();
    }
}
