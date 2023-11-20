package Geek.Blog.dto;

import Geek.Blog.entity.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
//signUp = 회원가입
public class SignUpRequestDTO {
    @NotBlank(message = "이메일를 입력해주세요.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    @NotBlank(message = "계정을 입력해주세요")
    private String account;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String checkPassword;

    private Gender gender;

    @NotBlank(message = "생일을 입력해주세요.")
    private Date birthday;

    private Set<Interest> interests;

    private List<Authority> roles = new ArrayList<>();

    @Builder
    public Member toEntity(){
        return Member.builder()
                .email(email)
                .account(account)
                .password(password)
                .gender(gender)
                .birthday(birthday)
                .interests(interests)
                .roles(roles)
                .build();
    }
}