package Geek.Blog.entity;

import Geek.Blog.dto.MemberDto;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder

@Getter
@Setter

//@Table(name = "Member")
public class Member {
    @Id @Column (name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
    private Long id;

    @Column
    private String name;

    @Column(unique = true, length = 45)
    private String email;

    @Column(length = 50)
    private String password;

    @Column
    private String interest;

    @Column
    private Date birthday;

    @Enumerated(EnumType.STRING)
    private Role role;

    public void addUserAuthority() {
        this.role = Role.USER;
    }

    public void encodePassword(PasswordEncoder passwordEncoder){
        this.password = passwordEncoder.encode(password);
    }

    public static Member toMemberEntity(MemberDto memberDto){
        Member memberEntity = new Member();
        memberEntity.setId(memberDto.getId());
        memberEntity.setEmail(memberDto.getEmail());
        memberEntity.setName(memberDto.getName());
        memberEntity.setPassword(memberDto.getPassword());
        memberEntity.setInterest(memberDto.getInterest());
        return memberEntity;
    }
}