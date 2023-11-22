package com.example.BlogPost.repository;

import com.example.BlogPost.DTO.BlogDTO;
import com.example.BlogPost.entity.Blog;

import java.util.Optional;

public interface BlogRepository {
    Blog create(BlogDTO blog);
    Optional<Blog> findById(Integer id);
    Blog edit(Blog blog);
    Integer deleteById(Integer id);
}