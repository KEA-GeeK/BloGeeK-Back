package Geek.Blog.controller;

import Geek.Blog.Response.SignInResponse;
import Geek.Blog.Response.SignUpResponse;
import Geek.Blog.dto.MemberDto;
import Geek.Blog.dto.SignInRequestDTO;
import Geek.Blog.dto.SignUpRequestDTO;
import Geek.Blog.entity.Member;
import Geek.Blog.repository.MemberRepository;
import Geek.Blog.service.Impl.MemberServiceImpl;
import Geek.Blog.service.Impl.TokenService;
import Geek.Blog.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.Optional;


@Slf4j
@RestController
@RequestMapping("/api/member")
//@RequiredArgsConstructor
public class MemberController {

    private final MemberServiceImpl memberService;
    private final TokenService tokenService;
    private MemberRepository memberRepository;
    private JwtTokenProvider jwtTokenProvider;


    @Autowired // 이 어노테이션을 추가
    public MemberController(MemberServiceImpl memberService, TokenService tokenService) {
        this.memberService = memberService;
        this.tokenService = tokenService;
    }


    @PostMapping("/login")
    public ResponseEntity<SignInResponse> login(@RequestBody SignInRequestDTO request) throws Exception{
        SignInResponse response = memberService.login(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/join")
    public ResponseEntity<SignUpResponse> join(@RequestBody SignUpRequestDTO request) {
        try {
            // 회원가입 성공 시 성공 메시지를 받아옴
            String signUpResult = memberService.signUp(request);
            // 성공 메시지를 이용하여 응답 객체를 생성
            SignUpResponse response = new SignUpResponse(signUpResult);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            // 실패 시 에러 메시지를 응답 객체에 담아서 반환
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new SignUpResponse("Error during sign-up: " + e.getMessage()));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String token) {
        // Perform logout logic (invalidate token, etc.)
        return ResponseEntity.ok("Logout successful");
    }

    @PostMapping("/delete_account")
    public ResponseEntity<String> deleteAccount(@RequestHeader("Authorization") String token) {
        try {
            String userId = jwtTokenProvider.extractIdx(token);
            Long parsedUserId = Long.parseLong(userId);
            memberService.withdraw(parsedUserId);
            return ResponseEntity.ok("Account deleted");
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid user ID format");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting account");
        }
    }


//    @DeleteMapping("/withdraw")
//    public ResponseEntity<String> withdraw(@RequestHeader(name = "Authorization", required = false) String token) {
//        try {
//            log.info("회원탈퇴 요청");
//            if (token == null || !token.startsWith("Bearer ")) {
//                throw new RuntimeException("유효한 토큰이 제공되지 않았습니다.");
//            }
//
//
//            String accessToken = token.substring(7); // "Bearer " 이후의 토큰 값 추출
//            log.info("토큰: " + accessToken);
//
//            Long userId = Long.parseLong(tokenService.extractUserId(accessToken)); // 토큰에서 사용자 식별자 추출
//            log.info("사용자 식별자: " + userId);
//
//            // 반환값을 사용하지 않도록 수정
//            memberService.withdraw(userId);
//
//            return ResponseEntity.ok("회원 탈퇴가 성공적으로 처리되었습니다.");
//
//        } catch (Exception e) {
//            log.error("회원 탈퇴 중 오류 발생: {}", e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원 탈퇴 중 오류가 발생했습니다.");
//        }
//    }
//

//    @DeleteMapping("/withdraw")
//    public ResponseEntity<String> withdraw(@RequestHeader(name = "Authorization", required = false) String token) {
//        try {
//            log.info("회원탈퇴 요청");
//
//            if (token == null || !token.startsWith("Bearer ")) {
//                throw new RuntimeException("유효한 토큰이 제공되지 않았습니다.");
//            }
//
//            String accessToken = token.substring(7); // "Bearer " 이후의 토큰 값 추출
//            log.info("토큰: " + accessToken);
//
//            Long userId = Long.parseLong(tokenService.extractUserId(accessToken)); // 토큰에서 사용자 식별자 추출
//            log.info("사용자 식별자: " + userId);
//
//            // 반환값을 사용하지 않도록 수정
//            memberService.withdraw(userId, accessToken);
//
//            return ResponseEntity.ok("회원 탈퇴가 성공적으로 처리되었습니다.");
//        } catch (Exception e) {
//            log.error("회원 탈퇴 중 오류 발생: {}", e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원 탈퇴 중 오류가 발생했습니다.");
//        }
//    }


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