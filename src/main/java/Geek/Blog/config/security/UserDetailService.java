package Geek.Blog.config.security;

import Geek.Blog.entity.Member;
import Geek.Blog.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {

    @Autowired
    private final MemberRepository memberRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByAccount(username).orElseThrow(
                () -> new UsernameNotFoundException("Could not found user" + username)
        );

        log.info("Success find member {}", member);

//        return User.builder()
//                .username(member.getAccount())
//                .password(member.getPassword())
//                .roles("USER")
//                .build();
//        return new CustomUserDetails(member);
        return new CustomUserDetails(member);
    }
}
