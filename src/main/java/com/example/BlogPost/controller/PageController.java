package com.example.BlogPost.controller;

import com.example.BlogPost.entity.Post;
import com.example.BlogPost.service.PostService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

//TODO Comment Controller, Reply들 추가
@Controller
public class PageController {

    private final PostService postService;

    @Autowired
    public PageController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/")
    public String home(){
        return "home";
    }

    @GetMapping("/posts/write")
    public String write(){
        return "post/writePost";
    }

    @GetMapping("/posts/all")
    public String getList(Model model){
        List<Post> posts = postService.listPosts();
        model.addAttribute("posts", posts);
        return "post/viewAllPosts";
    }

    @GetMapping("/posts/view/{id}")
    public String viewPost(@PathVariable Integer id){
        Post post = postService.viewPost(id).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 게시글입니다."));
        return "post/viewPost";
    }

    @GetMapping("/posts/edit/{id}")
    public String editView(@PathVariable Integer id){
        Post post = postService.viewPost(id).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 게시글입니다."));
        return "post/editPost";
    }
}

