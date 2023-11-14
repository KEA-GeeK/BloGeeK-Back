package Geek.Blog.dto;

import Geek.Blog.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class MemberDto { //회원 정보를 필드로 정의
    private Long id;
    private String name;
    private String email;
    private String password;
    private String interest;

    //lombok 어노테이션으로 getter,setter,생성자,toString 메서드 생략 가능
    public MemberDto(Member member) {
        this.id = member.getId();
        this.email = member.getEmail();
        this.name = member.getName();
        this.password = member.getPassword();
        this.interest = member.getInterest();
    }
}
//MemberDto Class`