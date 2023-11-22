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
        return postService.viewPost(postId).orElseThrow(() -> new EntityNotFoundException("Invalid ID"));
    }

    @DeleteMapping("/{postId}")
    public String deletePost(@PathVariable Integer postId) {
        Post post = postService.viewPost(postId).orElseThrow(() -> new EntityNotFoundException("Invalid ID"));
        postService.deletePost(post);
        return "Deleted successfully";
    }

    @PatchMapping("/{postId}")
    public Post editPost(@PathVariable Integer postId, @RequestBody PostDTO form) {  // @PathVariable 및 @RequestBody 사용
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
