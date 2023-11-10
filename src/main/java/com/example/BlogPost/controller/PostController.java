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
    public Integer createPost(@RequestBody PostDTO post) {
        return postService.upload(post);
    }

    @GetMapping("/all")
    public List<Post> getPostList() {
        return postService.listPosts();
    }

    @GetMapping("/{id}")
    public Post viewPost(@PathVariable Integer id) {
        return postService.viewPost(id).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 게시글입니다."));
    }

    @DeleteMapping("/{id}")
    public String deletePost(@PathVariable Integer id) {
        Post post = postService.viewPost(id).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 게시글입니다."));
        postService.deletePost(post);
        return "Deleted successfully";
    }

    @PatchMapping("/edit/{id}")
    public Post editPost(@PathVariable Integer id, @RequestBody PostDTO form) {  // @PathVariable 및 @RequestBody 사용
        Post post = postService.viewPost(id).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 게시글입니다."));
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
