package Geek.Blog.service;

import Geek.Blog.jwt.JwtUtil;
import Geek.Blog.dto.MemberDTO;
import Geek.Blog.entity.Member;
import Geek.Blog.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@EnableWebSecurity
public class MemberService {

    @Value("${jwt:secret:key")
    private String secretKey;
    private Long expiredMs = 1000 * 60 * 0l;

    public String login(String username){
        return JwtUtil.createJwt(username, secretKey, expiredMs);
    }

    private final MemberRepository memberRepository;

    public void save(MemberDTO memberDTO){
        Member member = Member.toMemberEntity(memberDTO);
        memberRepository.save(member);
    }


    // Todo: Member Class 대신 LoginReqDto 받아서 id를 기반으로 사용자 조회하고 로그인 처리하도록 수정
//    public Member login(Member member) throws Exception {
//        return memberRepository.findByEmail(member.getEmail())
//                .orElseThrow(() -> new Exception("존재하지 않는 사용자"));
//    }


    public MemberDTO findById(Long id) {
        Optional<Member> optionalMemberEntity = memberRepository.findById(id);
        if (optionalMemberEntity.isPresent()){
            return MemberDTO.toMemberDTO(optionalMemberEntity.get()); // optional을 벗겨내서 entity -> dto 변환
        }else {
            return null;
        }
    }

    public MemberDTO deleteById(Long id) {
        Optional<Member> optionalMemberEntity = memberRepository.findById(id);
        if (optionalMemberEntity.isPresent()) {
            Member member = optionalMemberEntity.get();
            memberRepository.deleteById(id);
            return MemberDTO.toMemberDTO(member);
        } else {
            return null;
        }
    }
}