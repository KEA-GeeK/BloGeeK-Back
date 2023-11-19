package Geek.Blog.controller;

import Geek.Blog.Response.LoginResponse;
import Geek.Blog.Response.SignUpResponse;
import Geek.Blog.dto.MemberDto;
import Geek.Blog.dto.SignUpRequestDTO;
import Geek.Blog.dto.SignInRequestDTO;
import Geek.Blog.repository.MemberRepository;
import Geek.Blog.service.Impl.MemberServiceImpl;
import Geek.Blog.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberServiceImpl memberService;
    private final MemberRepository memberRepository;


    //다시
    @PostMapping("/logIn")
    public ResponseEntity<LoginResponse> logIn(@RequestBody SignInRequestDTO request) {
        try {
            String token = memberService.signIn(request);

            if (token != null) {
                return ResponseEntity.status(HttpStatus.OK).body(new LoginResponse((token)));
                //return ResponseEntity.status(HttpStatus.OK).body(token); // 로그인 성공 시 토큰 반환
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse("로그인 실패"));
                //return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패"); // 로그인 실패 시 응답
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new LoginResponse(("")));
            //return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(""); // 서버 오류 시 응답
        }
    }

    @PostMapping("/join")
    public ResponseEntity<SignUpResponse> join(@RequestBody MemberDto request) {
        try {
            String token = memberService.signUp(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(new SignUpResponse((token)));// 회원가입 성공 시 회원 ID 반환
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new SignUpResponse("Error during sign-up"));// 회원가입 실패 시 응답
        }
    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<MemberDTO> findBy(@PathVariable Long id) {
//        MemberDto memberDTO = memberServiceImpl.findById(id);
//        if (memberDTO != null) {
//            return ResponseEntity.ok(memberDTO);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
////
//    @GetMapping("/delete/{id}")
//    public ResponseEntity<MemberDTO> deleteById(@PathVariable Long id) {
//        MemberDTO deletedMember = memberServiceImpl.deleteById(id);
//        if (deletedMember != null) {
//            return ResponseEntity.ok(deletedMember);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
}