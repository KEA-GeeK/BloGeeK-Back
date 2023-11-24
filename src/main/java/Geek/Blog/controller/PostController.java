package Geek.Blog.controller;

import Geek.Blog.dto.PostDTO;
import Geek.Blog.entity.Post;
import Geek.Blog.service.PostService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/write")
    public Post createPost(@RequestBody PostDTO postDTO) {
        Post post = postService.upload(postDTO);

        if (post == null) {
            throw new RuntimeException("게시글 등록에 실패했습니다.");
        }
        else{
            return post;
        }
    }

    @GetMapping("/all")
    public List<Post> getPostList() {
        return postService.listPosts();
    }

    @GetMapping("/{postId}")
    public Post viewPost(@PathVariable Long postId) {
        return postService.viewPost(postId).orElseThrow(() -> new EntityNotFoundException("Invalid ID"));
    }

    @DeleteMapping("/{postId}")
    public String deletePost(@PathVariable Long postId) {
        Post post = postService.viewPost(postId).orElseThrow(() -> new EntityNotFoundException("Invalid ID"));
        postService.deletePost(post);
        return "Deleted successfully";
    }

    @PatchMapping("/{postId}")
    public Post editPost(@PathVariable Long postId, @RequestBody PostDTO form) {  // @PathVariable 및 @RequestBody 사용
        Post post = postService.viewPost(postId).orElseThrow(() -> new EntityNotFoundException("Invalid ID"));
        if (form.getPost_title() == null || form.getContents() == null) {
            throw new EntityNotFoundException("Invalid Input");
        } else if (form.getPost_title().isBlank() || form.getContents().isBlank()){
            throw new EntityNotFoundException("Invalid Input");
        }

        post.setPost_title(form.getPost_title());
        post.setContents(form.getContents());
        postService.editPost(post);
        return post;
    }
}
