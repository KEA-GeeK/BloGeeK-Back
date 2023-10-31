package Geek.Blog.repository;

import Geek.Blog.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowRepository extends JpaRepository<Member, Long> {

    //디비에서 검색
    //-->서비스로 넘기기
    //findBy
}
