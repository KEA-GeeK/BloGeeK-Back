package Geek.Blog.controller;

import Geek.Blog.dto.LikeDTO;
import Geek.Blog.entity.Like;
import Geek.Blog.service.LikeService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/posts/{postId}/like")
public class LikeController {

    private final LikeService likeService;

    @Autowired
    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping("/")
    public ResponseEntity<?> createLike(@PathVariable Long postId, @RequestBody LikeDTO likeDTO) {
        likeDTO.setPost_id(postId);
        Like like = likeService.addLike(likeDTO);

        if (like == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("좋아요 추가에 실패했습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(new LikeDTO(like));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getLikeList(@PathVariable Long postId) {
        try {
            List<Like> likes = likeService.listLikesOfPost(postId);

            if (likes.isEmpty()){
                throw new EntityNotFoundException("Post not found with ID: " + postId);
            }

            List<LikeDTO> likeDTOS = likes.stream()
                    .map(LikeDTO::new) // Like 객체를 LikeDTO 객체로 변환
                    .collect(Collectors.toList()); // 결과를 List로 수집

            return ResponseEntity.ok(likeDTOS);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 내부 오류가 발생했습니다.");
        }
    }

}
