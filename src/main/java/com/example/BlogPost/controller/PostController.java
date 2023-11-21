package com.example.BlogPost.controller;

import com.example.BlogPost.DTO.PostDTO;
import com.example.BlogPost.entity.Post;
import com.example.BlogPost.service.PostService;
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
    public String createPost(@RequestBody PostDTO post) {
        Integer result = postService.upload(post);
        if (result == -1 ){
            return "Upload Failed";
        }
        else {
            return "Upload complete at ID " + result;
        }
    }

    @GetMapping("/all")
    public List<Post> getPostList() {
        return postService.listPosts();
    }

    @GetMapping("/{postId}")
    public Post viewPost(@PathVariable Integer postId) {
        return postService.viewPost(postId).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 게시글입니다."));
    }

    @DeleteMapping("/{postId}")
    public String deletePost(@PathVariable Integer postId) {
        Post post = postService.viewPost(postId).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 게시글입니다."));
        postService.deletePost(post);
        return "Deleted successfully";
    }

    @PatchMapping("/{postId}")
    public Post editPost(@PathVariable Integer postId, @RequestBody PostDTO form) {  // @PathVariable 및 @RequestBody 사용
        Post post = postService.viewPost(postId).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 게시글입니다."));
        if (form.getPost_title() == null || form.getContents() == null) {
            throw new EntityNotFoundException("입력값이 잘못되었습니다.");
        } else if (form.getPost_title().isBlank() || form.getContents().isBlank()){
            throw new EntityNotFoundException("입력값이 잘못되었습니다.");
        }

        post.setPost_title(form.getPost_title());
        post.setContents(form.getContents());
        postService.editPost(post);
        return post;
    }
}
