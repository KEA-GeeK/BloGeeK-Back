package Geek.Blog.controller;

import Geek.Blog.Response.PostResponseDTO;
import Geek.Blog.dto.PostDTO;
import Geek.Blog.dto.PostEditDTO;
import Geek.Blog.entity.Post;
import Geek.Blog.service.PostService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/write")
    public ResponseEntity<Object> createPost(@RequestBody PostDTO postDTO) {
        Post post = postService.upload(postDTO);

        if (post == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(new PostResponseDTO(post));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<PostResponseDTO>> getPostList() {
        List<Post> posts = postService.listPosts();
        List<PostResponseDTO> postResponseDTOs = posts.stream().map(PostResponseDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(postResponseDTOs);
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

    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId) {
        try {
            Post post = postService.viewPost(postId).orElseThrow(() -> new EntityNotFoundException("Invalid ID"));
            postService.deletePost(post);
            return ResponseEntity.ok("Deleted successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found with ID: " + postId);
        }
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<?> editPost(@PathVariable Long postId, @RequestBody PostEditDTO form) {
        try {
            Post post = postService.viewPost(postId).orElseThrow(() -> new EntityNotFoundException("Invalid ID"));

            if (form.getPost_title() == null || form.getContents() == null || form.getPost_title().isBlank() || form.getContents().isBlank()) {
                return ResponseEntity.badRequest().body("Invalid input");
            }

            post.setPost_title(form.getPost_title());
            post.setContents(form.getContents());
            postService.editPost(post);
            return ResponseEntity.ok(new PostResponseDTO(post));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found with ID: " + postId);
        }
    }

}
