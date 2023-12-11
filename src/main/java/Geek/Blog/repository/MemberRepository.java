package Geek.Blog.repository;

import Geek.Blog.entity.Member;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository{

    Optional<Member> findById(Long id);
    Optional<Member> findByAccount(String account);
}
