package Geek.Blog.dto;

import Geek.Blog.entity.Authority;
import Geek.Blog.entity.Blog;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class MemberDTO { //회원 정보를 필드로 정의
    private Long id;
    private String account;
    private List<Authority> roles = new ArrayList<>();
    private Blog blog;
}
//MemberDto Class`