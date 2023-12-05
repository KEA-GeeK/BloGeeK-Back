package Geek.Blog.service.Impl;

import Geek.Blog.Response.SignInResponse;
import Geek.Blog.dto.BlogDTO;
import Geek.Blog.dto.MemberDto;
import Geek.Blog.dto.SignInRequestDTO;
import Geek.Blog.dto.SignUpRequestDTO;
import Geek.Blog.entity.Member;
import Geek.Blog.repository.BlogRepository;
import Geek.Blog.repository.MemberRepository;
import Geek.Blog.service.MemberService;
import Geek.Blog.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
//@Builder
@Service
@Transactional
@RequiredArgsConstructor
@EnableWebSecurity
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final BlogRepository blogRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private  TokenService tokenService;



    //로그인
    public SignInResponse login(SignInRequestDTO request) throws Exception {

        Optional<Member> optionalMember = memberRepository.findByAccount(request.getAccount());

        Member member = optionalMember.orElseThrow(() ->
                new BadCredentialsException("계정를 찾을 수 없습니다."));

//        System.out.println("입력한 비밀번호: " + request.getPassword());
//        System.out.println("저장된 비밀번호: " + member.getPassword());
//
//        logger.info("입력한 비밀번호: {}", request.getPassword());
//        logger.info("저장된 비밀번호: {}", member.getPassword());

        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            System.out.println("비밀번호 불일치: " + request.getPassword() + " / " + member.getPassword());
            throw new BadCredentialsException("잘못된 비밀번호 입니다.");
        }

        // 로그인 성공 시 토큰 업데이트
        String newToken = generateToken(member.getEmail(), request.getPassword(), member.getId(), request.getPassword());
        member.updateToken(newToken);

        log.info("Token updated successfully: {}", newToken);

        return SignInResponse.builder()
                .id(member.getId())
                .account(member.getAccount())
                .email(member.getEmail())
                .password(member.getPassword())
                .gender(member.getGender())
                .birthday(member.getBirthday())
                .interests(member.getInterests())
                .roles(member.getRoles())
                //.token(generateToken(member.getEmail(), request.getPassword(), member.getId(), request.getPassword()))
                .token(newToken)
                .build();
    }

    /**
     * 토큰 발행
     *
     * @param principal   로그인 시도 아이디
     * @param credentials 로그인 시도 비밀번호
     * @param memberIdx   사용자 식별자
     * @param password    사용자 비밀번호
     * @return 토큰이 들어있는 객체
     */

    //토큰 발급
    public String generateToken(Object principal, Object credentials, Long memberIdx, String password) throws Exception {
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(principal, credentials);
            log.info(authenticationToken.toString());
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            log.info(authentication.toString());

            // 로그인 성공 시에만 토큰을 생성
            if (authentication.isAuthenticated()) {
                return jwtTokenProvider.createTokens(authentication, memberIdx, password);
            } else {
                return null; // 로그인 실패 시에는 토큰을 생성하지 않음
            }

            //return jwtTokenProvider.createTokens(authentication, memberIdx, password);
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
            return null;
        }
    }


    //회원가입
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String signUp(SignUpRequestDTO request) throws Exception {
        log.info(request.toString());

        if (memberRepository.findByAccount(request.getEmail()).isPresent()) {
            throw new Exception("이미 가입된 이메일입니다.");
        }

        Member member = memberRepository.save(new Member(request));
        member.setPassword(passwordEncoder.encode(request.getPassword()));
        log.info("생성된 회원: " + member);
//
//        BlogDTO blogDTO = new BlogDTO(member);
//        blogRepository.create(blogDTO);

        // 회원가입 후, 회원의 토큰를 반환
        //return generateToken(member.getEmail(), requestDto.getPassword(), member.getId(), requestDto.getPassword());
        return "회원가입에 성공했습니다.";
    }

    //회원탈퇴
//
    public void withdraw(Long userId) throws Exception {
        try {
            log.info("사용자 정보: " + userId);
            // 반환값을 사용하지 않도록 수정

            memberRepository.deleteById(userId);
        } catch (Exception e) {
            log.error("회원 탈퇴 중 오류 발생: {}", e.getMessage());
            throw e;
        }
    }
}