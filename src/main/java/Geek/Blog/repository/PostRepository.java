package Geek.Blog.repository;

import Geek.Blog.dto.PostDTO;
import Geek.Blog.entity.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {
    Optional<Post> upload(PostDTO postDTO);
    Optional<Post> findById(Long id);
    Optional<Post> findByTitle(String title);
    List<Post> findAll();

    Post edit(Post post);
    Integer deleteById(Long id);
}