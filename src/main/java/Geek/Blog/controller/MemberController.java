package Geek.Blog.controller;

import Geek.Blog.Response.SignInResponse;
import Geek.Blog.Response.SignUpResponse;
import Geek.Blog.config.security.CustomUserDetails;
import Geek.Blog.dto.MemberDto;
import Geek.Blog.dto.SignInRequestDTO;
import Geek.Blog.repository.MemberRepository;
import Geek.Blog.service.Impl.MemberServiceImpl;
import Geek.Blog.service.Impl.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberServiceImpl memberService;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<SignInResponse> login(@RequestBody SignInRequestDTO request) throws Exception {
        return new ResponseEntity<>(memberService.login(request), HttpStatus.OK);
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


    @DeleteMapping("/withdraw")
    public ResponseEntity<String> withdraw(@RequestHeader("Authorization") String token) {
        try {
            log.info("회원탈퇴 요청");
            if (token == null || !token.startsWith("Bearer ")) {
                throw new RuntimeException("유효한 토큰이 제공되지 않았습니다.");
            }


            String accessToken = token.substring(7); // "Bearer " 이후의 토큰 값 추출
            log.info("토큰: " + accessToken);

            Long userId = Long.parseLong(tokenService.extractUserId(accessToken)); // 토큰에서 사용자 식별자 추출
            log.info("사용자 식별자: " + userId);

            // 반환값을 사용하지 않도록 수정
            memberService.withdraw(userId);

            return ResponseEntity.ok("회원 탈퇴가 성공적으로 처리되었습니다.");

        } catch (Exception e) {
            log.error("회원 탈퇴 중 오류 발생: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원 탈퇴 중 오류가 발생했습니다.");
        }
    }


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