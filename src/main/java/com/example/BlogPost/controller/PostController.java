package com.example.BlogPost.controller;

import com.example.BlogPost.entity.Post;
import com.example.BlogPost.service.PostService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

//TODO 사용자의 요청이 유효한지 검사 try catch
@Controller
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/post/write")
    public String write(){
        return "post/writePost";
    }

    @PostMapping("/post/write")
    public String create(PostForm form) {
        Post post = new Post(form.getTitle(), form.getContent());

        postService.upload(post);

        return "redirect:/";
    }

    @GetMapping("/post/allposts")
    public String getList(Model model){
        List<com.example.BlogPost.entity.Post> posts = postService.listPosts();
        model.addAttribute("posts", posts);
        return "post/viewAllPosts";
    }

    @PostMapping("/post/allposts")
    public  String delete(PostForm form){
        Integer id = Integer.valueOf(form.getId());
        Post post = postService.viewPost(id).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 게시글입니다."));
        Integer result =  postService.deletePost(post);

        return "redirect:/";
    }

    @GetMapping("/post/view")
    public String viewPost(HttpServletRequest request, Model model){
        Integer id = Integer.valueOf(request.getParameter("id"));
        Post post = postService.viewPost(id).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 게시글입니다."));
        model.addAttribute("post", post);
        return "post/viewPost";
    }

    @GetMapping("/post/edit")
    public String editView(HttpServletRequest request, Model model){
        Integer id = Integer.valueOf(request.getParameter("id"));
        Post post = postService.viewPost(id).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 게시글입니다."));
        model.addAttribute("post", post);
        return "post/editPost";
    }

    @PostMapping("/post/edit")
    public String editPost(PostForm form) {
        Post post = postService.viewPost(form.getId()).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 게시글입니다."));

        post.setPost_title(form.getTitle());
        post.setContents(form.getContent());

        postService.editPost(post);

        return "redirect:/post/allposts";
    }
}