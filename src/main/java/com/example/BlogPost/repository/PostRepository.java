package com.example.BlogPost.repository;

import com.example.BlogPost.entity.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {
    Post upload(Post post);
    Optional<Post> findById(Integer id);
    Optional<Post> findByTitle(String title);
    List<Post> findAll();

    Post edit(Post post);
    Integer deleteById(Integer id);
}