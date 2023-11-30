package Geek.Blog.controller;

import Geek.Blog.dto.LikeDTO;
import Geek.Blog.entity.Like;
import Geek.Blog.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/posts/{postsId}/like")
public class LikeController {

    private final LikeService likeService;

    @Autowired
    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping("/")
    public ResponseEntity<?> createLike(@PathVariable Long postsId, @RequestBody LikeDTO likeDTO) {
        likeDTO.setPost_id(postsId);
        Like like = likeService.addLike(likeDTO);

        if (like == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("좋아요 추가에 실패했습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(new LikeDTO(like));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<LikeDTO>> getLikeList(@PathVariable Long postsId) {
        List<Like> likes = likeService.listLikesOfPost(postsId);
        List<LikeDTO> likeDTOS = likes.stream()
                .map(LikeDTO::new) // Like 객체를 LikeDTO 객체로 변환
                .collect(Collectors.toList()); // 결과를 List로 수집

        return ResponseEntity.ok(likeDTOS);
    }
}
