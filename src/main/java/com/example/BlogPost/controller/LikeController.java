package com.example.BlogPost.controller;

import com.example.BlogPost.DTO.LikeDTO;
import com.example.BlogPost.entity.Like;
import com.example.BlogPost.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts/like")
public class LikeController {

    private final LikeService likeService;

    @Autowired
    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping("/")
    public Like createLike(@RequestBody LikeDTO likeDTO) {

        Like like = likeService.addLike(likeDTO);

        if (like == null ){
            throw new RuntimeException("좋아요 추가에 실패했습니다.");
        }
        else {
            return like;
        }
    }

    @GetMapping("/all")
    public List<Like> getLikeList() {
        return likeService.listLikes();
    }
}
