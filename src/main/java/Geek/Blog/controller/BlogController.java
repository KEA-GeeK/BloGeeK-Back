package Geek.Blog.controller;

import Geek.Blog.dto.BlogDTO;
import Geek.Blog.dto.BlogEditDTO;
import Geek.Blog.entity.Blog;
import Geek.Blog.service.BlogService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
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
    public BlogDTO viewBlog(@PathVariable Long blogId) {
        return blogService.viewBlog(blogId) //Optional<Post>
                .map(BlogDTO::new) // Blog 객체를 BlogResponseDTO로 변환
                .orElseThrow(() -> new EntityNotFoundException("Invalid ID"));
    }

    @PatchMapping("/{blogId}")
    public BlogDTO editBlog(@PathVariable Long blogId, @RequestBody BlogEditDTO form) {  // @PathVariable 및 @RequestBody 사용
        Blog blog = blogService.viewBlog(blogId).orElseThrow(() -> new EntityNotFoundException("Invaild ID"));
        if (form.getBlog_name() == null) {
            throw new EntityNotFoundException("Invalid Input");
        } else if (form.getBlog_name().isBlank()){
            throw new EntityNotFoundException("Invalid Input");
        }

        blog.setBlog_name(form.getBlog_name());
        blog.setAbout_blog(form.getAbout_blog());
        blog.setProfile_picture(form.getProfilePicture());

        blogService.editBlog(blog);

        return new BlogDTO(blog);
    }
}
