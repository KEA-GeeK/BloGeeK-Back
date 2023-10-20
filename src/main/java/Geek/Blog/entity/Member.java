package Geek.Blog.entity;

import Geek.Blog.dto.MemberDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
    private Long id;

    @Column
    private String name;

    @Column(unique = true)
    private String email;

    @Column
    private String password;

    public static Member toMemberEntity(MemberDTO memberDto){
        Member memberEntity = new Member();
        memberEntity.setId(memberDto.getId());
        memberEntity.setEmail(memberDto.getEmail());
        memberEntity.setName(memberDto.getName());
        memberEntity.setPassword(memberDto.getPassword());
        return memberEntity;
    }
}