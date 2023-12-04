package Geek.Blog.controller;

import Geek.Blog.Response.PostResponseDTO;
import Geek.Blog.entity.Post;
import Geek.Blog.service.PostService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/posts")
public class PostNonMemController {

    private final PostService postService;

    @Autowired
    public PostNonMemController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getPostList() {
        try {
            List<Post> posts = postService.listPosts();

            if (posts == null) {
                throw new EntityNotFoundException("게시글이 존재하지 않습니다.");
            }

            List<PostResponseDTO> postResponseDTOs = posts.stream().map(PostResponseDTO::new).collect(Collectors.toList());

            return ResponseEntity.ok(postResponseDTOs);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 내부 오류가 발생했습니다.");
        }
    }

    @GetMapping("/{postId}")
    public ResponseEntity<?> viewPost(@PathVariable Long postId) {
        try {
            Post post = postService.viewPost(postId).orElseThrow(() -> new EntityNotFoundException("Post not found with ID: " + postId));
            return ResponseEntity.ok(new PostResponseDTO(post));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
