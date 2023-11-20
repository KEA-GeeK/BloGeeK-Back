package Geek.Blog.service.Impl;

import Geek.Blog.Response.SignInResponse;
import Geek.Blog.dto.MemberDto;
import Geek.Blog.dto.SignInRequestDTO;
import Geek.Blog.entity.Member;
import Geek.Blog.repository.MemberRepository;
import Geek.Blog.service.MemberService;
import Geek.Blog.util.JwtTokenProvider;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;

import java.util.Optional;

@Slf4j
@Builder
@Service
@RequiredArgsConstructor
@EnableWebSecurity
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;



    //다시
//    public String login(String email, String password) throws Exception {
//        // 데이터베이스에서 이메일과 비밀번호를 확인하여 로그인 처리
//        Member member = memberRepository.findByAccount(email)
//                .orElse(null);
//        if (member != null && member.getPassword().equals(password)) {
//            // 로그인 성공시 JWT 토큰 생성
//            String token = generateToken(member.getEmail(), member.getPassword(), member.getId(), member.getPassword());
//            return token;
//        } else {
//            // 로그인 실패
//            return null;
//        }
//    }
    private static final Logger logger = LoggerFactory.getLogger(MemberServiceImpl.class);

    public SignInResponse login(SignInRequestDTO request) throws Exception{

        Optional<Member> optionalMember = memberRepository.findByAccount(request.getAccount());

       // Member member = memberRepository.findByAccount(request.getAccount()).orElseThrow(() ->
        Member member = optionalMember.orElseThrow(() ->
                new BadCredentialsException("계정를 찾을 수 없습니다."));

        System.out.println("입력한 비밀번호: " + request.getPassword());
        System.out.println("저장된 비밀번호: " + member.getPassword());

        logger.info("입력한 비밀번호: {}", request.getPassword());
        logger.info("저장된 비밀번호: {}", member.getPassword());

//        if (member == null) {
//            throw new BadCredentialsException("멤버 정보 null");
//        }
        if(!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            System.out.println("비밀번호 불일치: " + request.getPassword() + " / " + member.getPassword());
            throw new BadCredentialsException("잘못된 비밀번호 입니다.");
        }

        return SignInResponse.builder()
                .id(member.getId())
                .account(member.getAccount())
                .email(member.getEmail())
                .password(member.getPassword())
                .gender(member.getGender())
                .birthday(member.getBirthday())
                .interests(member.getInterests())
                .roles(member.getRoles())
                .token(generateToken(member.getEmail(), request.getPassword(), member.getId(), request.getPassword()))
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
    public String generateToken(Object principal, Object credentials, Long memberIdx, String password) throws Exception {
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(principal, credentials);
            log.info(authenticationToken.toString());

            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);


            log.info(authentication.toString());
            return jwtTokenProvider.createTokens(authentication, memberIdx, password);
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
            return null;
        }
    }

    //signIn = 로그인
    //signUp = 회원가입
//    @Override
//    public String signIn(SignInRequestDTO requestDto) throws Exception {
//        // 로그인 처리
//        String account = requestDto.getAccount();
//        String password = requestDto.getPassword();
//
//        String token = login(account, password);
//
//        if (token == null) {
//            throw new Exception("로그인 실패"); // 로그인 실패 예외 처리
//        }
//
//        return token; // 로그인 성공시 토큰 반환
//    }

    @Override
    public String signUp(MemberDto requestDto) throws Exception {
        log.info(requestDto.toString());

        if (memberRepository.findByAccount(requestDto.getEmail()).isPresent()) {
            throw new Exception("이미 가입된 이메일입니다.");
        }

        Member member = memberRepository.save(new Member(requestDto));
        member.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        log.info("생성된 회원: " + member);

        // 회원가입 후, 회원의 토큰를 반환
        return generateToken(member.getEmail(), requestDto.getPassword(), member.getId(), requestDto.getPassword());
    }
}