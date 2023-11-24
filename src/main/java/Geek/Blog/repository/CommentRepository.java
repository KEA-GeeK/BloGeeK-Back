package Geek.Blog.repository;

import Geek.Blog.dto.CommentDTO;
import Geek.Blog.entity.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {
    Comment upload(CommentDTO commentDTO);
    Optional<Comment> findById(Long id);
    List<Comment> findPostComment(Long postId);
    Comment edit(Comment comment);
    Integer deleteById(Long id);
}