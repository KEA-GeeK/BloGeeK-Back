package Geek.Blog.controller;

import Geek.Blog.dto.LoginDTO;
import Geek.Blog.dto.MemberDTO;
import Geek.Blog.entity.Member;
import Geek.Blog.repository.MemberRepository;
import Geek.Blog.service.MemberService;
import ch.qos.logback.core.model.Model;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api//member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @GetMapping("/save")
    public String saveForm(){
        return "save~";
    }
    @PostMapping("/save")    //name값을 repquestparam에 담아온다.
    public Member save(@RequestBody Member member) {
        log.info(member.toString());
        memberRepository.save(member);
        return member;
    }

//    @PostMapping("/login")
//    public String login(@RequestBody Member data) {
//        try {
//            memberService.login(data);
//            return "success";
//        } catch (Exception e) {
//            log.error(e.getMessage());
//            return "fail";
//        }
//    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO dto){
        return ResponseEntity.ok().body(memberService.login(dto.getUsername()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberDTO> findBy(@PathVariable Long id) {
        MemberDTO memberDTO = memberService.findById(id);
        if (memberDTO != null) {
            return ResponseEntity.ok(memberDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
//
    @GetMapping("/delete/{id}")
    public ResponseEntity<MemberDTO> deleteById(@PathVariable Long id) {
        MemberDTO deletedMember = memberService.deleteById(id);
        if (deletedMember != null) {
            return ResponseEntity.ok(deletedMember);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
