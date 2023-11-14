package Geek.Blog.service;

import Geek.Blog.dto.MemberDto;
import Geek.Blog.dto.SignInRequestDTO;
import Geek.Blog.dto.SignUpRequestDTO;
import jakarta.validation.Valid;

public interface MemberService {


    public String signIn(SignInRequestDTO requestDto) throws Exception;

    public Long signUp(MemberDto requestDto) throws Exception;

}
