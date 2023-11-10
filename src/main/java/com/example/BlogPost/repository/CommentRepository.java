package com.example.BlogPost.repository;

import com.example.BlogPost.DTO.CommentDTO;
import com.example.BlogPost.entity.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {
    CommentDTO upload(CommentDTO commentDTO);
    Optional<Comment> findById(Integer id);
    List<Comment> findAll();
    Comment edit(Comment comment);
    Integer deleteById(Integer id);
}