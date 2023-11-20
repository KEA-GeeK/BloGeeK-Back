package Geek.Blog.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Setter
//signIn = 로그인
public class SignInRequestDTO {
//    private String email;
//    private Long id;
//    private String account;
//    private String password;
//    private Gender gender;
//    private Date birthday;
//    private Set<Interest> interests;
//    private Role role;


    private String account;
    private String password;
}
