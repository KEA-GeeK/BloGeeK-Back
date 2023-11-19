package Geek.Blog.entity;

import Geek.Blog.dto.MemberDto;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.Set;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter

@Table(name = "Member")
public class Member {

    @Column(unique = true, length = 45)
    private String email;

    @Id @Column (name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
    private Long id;

    @Column(length = 50)
    private String password;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column
    private Date birthday;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "member_interests", joinColumns = @JoinColumn(name = "member_id"))
    @Column(name = "interest")
    private Set<Interest> interests;

    @Enumerated(EnumType.STRING)
    private Role role;

//    public void addUserAuthority() {
//        this.role = Role.USER;
//    }

    public void encodePassword(PasswordEncoder passwordEncoder){
        this.password = passwordEncoder.encode(password);
    }

    public Member(MemberDto memberDto){
        this.setEmail(memberDto.getEmail());
        this.setId(memberDto.getId());
        this.setPassword(memberDto.getPassword());
        this.setGender(memberDto.getGender());
        this.setBirthday((memberDto.getBirthday()));
        this.setInterests(memberDto.getInterests());
        this.role = Role.USER;
    }
}