package Geek.Blog.dto;

import Geek.Blog.entity.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class MemberDto { //회원 정보를 필드로 정의
    private String email;
    private Long id;
    private String account;
    private String password;
    private Gender gender;
    private Date birthday;
    private Set<Interest> interests;
    private List<Authority> roles = new ArrayList<>();
    private String token;
}
//MemberDto Class`