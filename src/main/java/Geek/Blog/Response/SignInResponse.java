package Geek.Blog.Response;

import Geek.Blog.entity.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SignInResponse {
    private Long id;
    private String account;
    private String email;
    private String password;
    private Gender gender;
    private Date birthday;
    private Set<Interest> interests;
    private List<Authority> roles = new ArrayList<>();
    private String token;


    public SignInResponse(Member member){
        this.id = member.getId();
        this.account = member.getAccount();
        this.email = member.getEmail();
        this.password = member.getPassword();
        this.gender = member.getGender();
        this.birthday = member.getBirthday();
        this.interests = member.getInterests();
        this.roles = member.getRoles();
        this.token = member.getToken();
    }
}