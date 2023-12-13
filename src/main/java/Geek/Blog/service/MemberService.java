package Geek.Blog.service;

import Geek.Blog.Response.SignInResponse;
import Geek.Blog.dto.SignInRequestDTO;
import Geek.Blog.dto.SignUpRequestDTO;

public interface MemberService {

    SignInResponse login(SignInRequestDTO request) throws Exception;
    String signUp(SignUpRequestDTO requestDto) throws Exception;

}
