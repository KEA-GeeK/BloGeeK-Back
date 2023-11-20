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

    //lombok 어노테이션으로 getter,setter,생성자,toString 메서드 생략 가능
//    public MemberDto(Member member) {
//        this.id = member.getId();
//        this.email = member.getEmail();
//        //this.name = member.getName();
//        this.password = member.getPassword();
//        this.interests = member.getInterests();
//    }
}
//MemberDto Class`