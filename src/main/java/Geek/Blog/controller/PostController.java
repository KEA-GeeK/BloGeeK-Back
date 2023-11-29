package Geek.Blog.controller;

import Geek.Blog.dto.PostDTO;
import Geek.Blog.Response.PostResponseDTO;
import Geek.Blog.dto.PostEditDTO;
import Geek.Blog.entity.Post;
import Geek.Blog.service.PostService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
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
    public PostResponseDTO createPost(@RequestBody PostDTO postDTO) {
        Post post = postService.upload(postDTO);

        if (post == null) {
            throw new RuntimeException("게시글 등록에 실패했습니다.");
        }
        else{
            return new PostResponseDTO(post);
        }
    }

    @GetMapping("/all")
    public List<PostResponseDTO> getPostList() {
        List<Post> posts = postService.listPosts();
        return posts.stream()
                .map(PostResponseDTO::new) // Post 객체를 PostResponseDTO 객체로 변환
                .collect(Collectors.toList()); // 결과를 List로 수집
    }

    @GetMapping("/{postId}")
    public PostResponseDTO viewPost(@PathVariable Long postId) {
        return postService.viewPost(postId) //Optional<Post>
                .map(PostResponseDTO::new) // Post 객체를 PostResponseDTO로 변환
                .orElseThrow(() -> new EntityNotFoundException("Invalid ID"));
    }

    @DeleteMapping("/{postId}")
    public String deletePost(@PathVariable Long postId) {
        Post post = postService.viewPost(postId).orElseThrow(() -> new EntityNotFoundException("Invalid ID"));
        postService.deletePost(post);
        return "Deleted successfully";
    }

    @PatchMapping("/{postId}")
    public PostResponseDTO editPost(@PathVariable Long postId, @RequestBody PostEditDTO form) {  // @PathVariable 및 @RequestBody 사용
        Post post = postService.viewPost(postId).orElseThrow(() -> new EntityNotFoundException("Invalid ID"));
        if (form.getPost_title() == null || form.getContents() == null) {
            throw new EntityNotFoundException("Invalid Input");
        } else if (form.getPost_title().isBlank() || form.getContents().isBlank()){
            throw new EntityNotFoundException("Invalid Input");
        }

        post.setPost_title(form.getPost_title());
        post.setContents(form.getContents());
        postService.editPost(post);
        return new PostResponseDTO(post);
    }
}
