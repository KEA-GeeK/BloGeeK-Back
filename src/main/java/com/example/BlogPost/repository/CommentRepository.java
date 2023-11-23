package com.example.BlogPost.repository;

import com.example.BlogPost.DTO.CommentDTO;
import com.example.BlogPost.entity.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {
    Comment upload(CommentDTO commentDTO);
    Optional<Comment> findById(Long id);
    List<Comment> findPostComment(Long postId);
    Comment edit(Comment comment);
    Integer deleteById(Long id);
}