package Geek.Blog.controller;

import Geek.Blog.Response.PostResponseDTO;
import Geek.Blog.dto.PostDTO;
import Geek.Blog.dto.PostDeleteDTO;
import Geek.Blog.dto.PostEditDTO;
import Geek.Blog.entity.Post;
import Geek.Blog.service.PostService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user/posts")
public class PostUserController {

    private final PostService postService;

    @Autowired
    public PostUserController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/write")
    public ResponseEntity<Object> createPost(@RequestBody PostDTO postDTO) {
        Optional<Post> post = postService.upload(postDTO);

        //post가 있으면 postResponseDTO를 반환, 없으면 에러메세지를 반환
        return post.<ResponseEntity<Object>>map(value -> ResponseEntity.status(HttpStatus.CREATED).body(new PostResponseDTO(value))).orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("게시글 업로드를 실패했습니다."));
    }

    @GetMapping("/list/{memberId}")
    public ResponseEntity<?> getPostList(@PathVariable Long memberId) {
        try {
            List<Post> posts = postService.listPostsByMember(memberId);
            if (posts.isEmpty()){
                throw new EntityNotFoundException("Post not found with MemberID: " + memberId);
            }

            List<PostResponseDTO> postResponseDTOS = posts.stream()
                    .map(PostResponseDTO::new) // Comment 객체를 CommentResponseDTO 객체로 변환
                    .collect(Collectors.toList()); // 결과를 List로 수집

            return ResponseEntity.ok(postResponseDTOS);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 내부 오류가 발생했습니다.");
        }
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId, @RequestBody PostDeleteDTO postDTO) {
        try {
            Post post = postService.viewPost(postId).orElseThrow(() -> new EntityNotFoundException("Invalid ID"));

            //유저 ID 검사
            if (Objects.equals(post.getAuthor().getId(), postDTO.getClaimer_id())){
                postService.deletePost(post);
                return ResponseEntity.ok("Deleted successfully");
            }
            else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Delete Denied");
            }
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found with ID: " + postId);
        }
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<?> editPost(@PathVariable Long postId, @RequestBody PostEditDTO form) {
        try {
            Post post = postService.viewPost(postId).orElseThrow(() -> new EntityNotFoundException("Invalid ID"));

            //입력값 검사
            if (form.getPost_title() == null || form.getContents() == null || form.getPost_title().isBlank() || form.getContents().isBlank()) {
                return ResponseEntity.badRequest().body("Invalid input");
            }

            //유저 ID 검사
            if (Objects.equals(post.getAuthor().getId(), form.getClaimer_id())){
                post.setPost_title(form.getPost_title());
                post.setContents(form.getContents());
                postService.editPost(post);
                return ResponseEntity.ok(new PostResponseDTO(post));
            }
            else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Delete Denied");
            }
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found with ID: " + postId);
        }
    }

}
