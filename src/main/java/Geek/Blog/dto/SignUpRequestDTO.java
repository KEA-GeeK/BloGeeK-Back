package Geek.Blog.dto;

import Geek.Blog.entity.Member;
import Geek.Blog.entity.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
//signUp = 회원가입
public class SignUpRequestDTO {
    @NotBlank(message = "아이디를 입력해주세요.")
    private String email;

    @NotBlank(message = "이름을 입력해주세요.")
    @Size(min=2, message = "이름이 너무 짧습니다.")
    private String name;

    @NotBlank(message = "생일을 입력해주세요.")
    private Date birthday;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    private String checkPassword;

    private Role role;

    @Builder
    public Member toEntity(){
        return Member.builder()
                .email(email)
                .name(name)
                .birthday(birthday)
                .password(password)
                .role(Role.USER)
                .build();

    }

}
