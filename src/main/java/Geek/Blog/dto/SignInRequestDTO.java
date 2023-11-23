package Geek.Blog.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Setter
//signIn = 로그인
public class SignInRequestDTO {
    private String account;
    private String password;
}
