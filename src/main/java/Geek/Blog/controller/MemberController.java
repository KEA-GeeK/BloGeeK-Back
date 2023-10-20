package Geek.Blog.controller;

import Geek.Blog.entity.Member;
import Geek.Blog.repository.MemberRepository;
import Geek.Blog.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @PostMapping("/save")    //name값을 repquestparam에 담아온다.
    public Member save(@RequestBody Member member) {
        log.info(member.toString());
        memberRepository.save(member);
        return member;
    }

    @PostMapping("/login")
    public String login(@RequestBody Member data) {
        try {
            memberService.login(data);
            return "success";
        } catch (Exception e) {
            log.error(e.getMessage());
            return "fail";
        }
    }
}
