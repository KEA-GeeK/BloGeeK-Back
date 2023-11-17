package Geek.Blog.controller;

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
   //private final MemberService memberService;
    private final MemberRepository memberRepository;


//    @PostMapping("/join")
//    @ResponseStatus(HttpStatus.OK)
//    public Long join(@Valid @RequestBody SignUpRequestDTO request) throws Exception {
//        return memberService.signUp(request);
//    }

    @PostMapping("/logIn")
    public ResponseEntity<String> logIn(@RequestBody SignInRequestDTO request) {
        try {
            String token = memberService.signIn(request);

            if (token != null) {
                return ResponseEntity.status(HttpStatus.OK).body(token); // 로그인 성공 시 토큰 반환
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패"); // 로그인 실패 시 응답
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류"); // 서버 오류 시 응답
        }
    }
    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody MemberDto request) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(memberService.signUp(request)); // 회원가입 성공 시 회원 ID 반환
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(""); // 회원가입 실패 시 응답
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