package Geek.Blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
//signIn = 로그인
public class SignInRequestDTO {
    private String email;
    private String password;


}
