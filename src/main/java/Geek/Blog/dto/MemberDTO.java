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
public class MemberDTO { //회원 정보를 필드로 정의
    private Long id;
    private String name;
    private String email;
    private String password;

    //lombok 어노테이션으로 getter,setter,생성자,toString 메서드 생략 가능
    public static MemberDTO toMemberDTO(Member memberEntity){
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(memberEntity.getId());
        memberDTO.setEmail(memberEntity.getEmail());
        memberDTO.setName(memberEntity.getName());
        memberDTO.setPassword(memberEntity.getPassword());

        return memberDTO;
    }
}
//MemberDto Class