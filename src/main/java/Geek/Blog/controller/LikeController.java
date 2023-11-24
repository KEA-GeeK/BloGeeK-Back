package Geek.Blog.controller;

import Geek.Blog.dto.LikeDTO;
import Geek.Blog.entity.Like;
import Geek.Blog.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/posts/like")
public class LikeController {

    private final LikeService likeService;

    @Autowired
    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping("/")
    public LikeDTO createLike(@RequestBody LikeDTO likeDTO) {

        Like like = likeService.addLike(likeDTO);

        if (like == null ){
            throw new RuntimeException("좋아요 추가에 실패했습니다.");
        }
        else {
            return new LikeDTO(like);
        }
    }

    @GetMapping("/all")
    public List<LikeDTO> getLikeList() {
        List<Like> likes = likeService.listLikes();
        return likes.stream()
                .map(LikeDTO::new) // Post 객체를 PostResponseDTO 객체로 변환
                .collect(Collectors.toList()); // 결과를 List로 수집
    }
}
