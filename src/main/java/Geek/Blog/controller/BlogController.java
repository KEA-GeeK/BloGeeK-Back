package Geek.Blog.controller;

import Geek.Blog.dto.BlogDTO;
import Geek.Blog.dto.BlogEditDTO;
import Geek.Blog.entity.Blog;
import Geek.Blog.service.BlogService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/blog")
public class BlogController {

    private final BlogService blogService;

    @Autowired
    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping("/{blogId}")
    public ResponseEntity<?> viewBlog(@PathVariable Long blogId) {
        try {
            Blog blog = blogService.viewBlog(blogId).orElseThrow(() -> new EntityNotFoundException("Blog not found with ID: " + blogId));
            return ResponseEntity.ok(new BlogDTO(blog));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PatchMapping("/{blogId}")
    public ResponseEntity<?> editBlog(@PathVariable Long blogId, @RequestBody BlogEditDTO form) {  // @PathVariable 및 @RequestBody 사용
        try {
            Blog blog = blogService.viewBlog(blogId).orElseThrow(() -> new EntityNotFoundException("Invalid ID"));
            if (form.getBlog_name() == null || form.getBlog_name().isBlank()) {
                return ResponseEntity.badRequest().body("Invalid input");
            }

            blog.setBlog_name(form.getBlog_name());
            blogService.editBlog(blog);
            return ResponseEntity.ok(new BlogDTO(blog));

        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found with ID: " + blogId);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
