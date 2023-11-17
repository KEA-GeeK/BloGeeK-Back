package Geek.Blog.controller;


import Geek.Blog.entity.Follow;
import Geek.Blog.repository.FollowRepository;
import Geek.Blog.service.FollowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/follow")
@RequiredArgsConstructor
public class FollowController {
    private final FollowService followService;
    private final FollowRepository followRepository;

    @PostMapping("/follower")
    public Follow follower(@RequestBody Follow follow) {
       //사용자의 요청이 유효한지 아닌지 검증\
        // 예) 토큰
    //try - catch'

        //log.info(Follower_id);
       return follow;
    }
    @PostMapping("/following")
    public Follow follwing(@RequestBody Follow follow){
        log.info(follow.getFollowing_id().toString());
        return follow;
    }
}
