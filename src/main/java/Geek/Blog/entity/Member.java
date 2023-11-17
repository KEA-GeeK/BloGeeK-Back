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
@ToString
@Getter
@Setter

@Table(name = "Member")
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

    public Member(MemberDto memberDto){
        this.setId(memberDto.getId());
        this.setEmail(memberDto.getEmail());
        this.setName(memberDto.getName());
        this.setPassword(memberDto.getPassword());
        this.setInterest(memberDto.getInterest());
        this.role = Role.USER;
    }
}