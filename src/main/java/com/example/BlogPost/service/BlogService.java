package com.example.BlogPost.service;

import com.example.BlogPost.DTO.BlogDTO;
import com.example.BlogPost.entity.Blog;
import com.example.BlogPost.repository.BlogRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

//TODO DB에서 받아온 데이터가 유효한지 try catch
@Transactional
@Service
public class BlogService {

    private final BlogRepository blogRepository;
    public BlogService(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }


    public Blog upload(BlogDTO blogDTO) {
        Blog blog = blogRepository.create(blogDTO);
        return blog;
    }

    public Optional<Blog> viewBlog(Integer blogId) {
            return blogRepository.findById(blogId);
    }

    public void editBlog(Blog blog){
        blogRepository.edit(blog);
    }
}
