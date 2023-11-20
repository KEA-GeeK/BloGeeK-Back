package Geek.Blog.entity;

import Geek.Blog.dto.MemberDto;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    @Column(unique = true, length = 20)
    private String account;

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

    @OneToMany(mappedBy = "member", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Builder.Default
    private List<Authority> roles = new ArrayList<>();

    private void setRoles(List<Authority> role){
        this.roles = role;
        role.forEach(o -> o.setMember(this));
    }


    public Member(MemberDto memberDto){
        this.setEmail(memberDto.getEmail());
        this.setId(memberDto.getId());
        this.setAccount(memberDto.getAccount());
        this.setPassword(memberDto.getPassword());
        this.setGender(memberDto.getGender());
        this.setBirthday((memberDto.getBirthday()));
        this.setInterests(memberDto.getInterests());
        this.setRoles(memberDto.getRoles());
    }
}