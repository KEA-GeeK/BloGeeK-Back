package Geek.Blog.dto;

import Geek.Blog.entity.Gender;
import Geek.Blog.entity.Interest;
import Geek.Blog.entity.Member;
import Geek.Blog.entity.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
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

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String checkPassword;

    private Gender gender;

    @NotBlank(message = "생일을 입력해주세요.")
    private Date birthday;

    private Set<Interest> interests;

    private Role role;

    @Builder
    public Member toEntity(){
        return Member.builder()
                .email(email)
                .password(password)
                .gender(gender)
                .birthday(birthday)
                .interests(interests)
                .role(Role.USER)
                .build();
    }
}