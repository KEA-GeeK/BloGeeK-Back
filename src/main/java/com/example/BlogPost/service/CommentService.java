package com.example.BlogPost.service;

import com.example.BlogPost.DTO.CommentDTO;
import com.example.BlogPost.entity.Comment;
import com.example.BlogPost.repository.CommentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    /**
     * 기능
     **/
    public Integer upload(CommentDTO commentDTO) {
        Comment comment = commentRepository.upload(commentDTO);

        if (comment.getComment_id() != null){
            return comment.getComment_id();
        }
        else {
            return -1;
        }

    }

    public Optional<Comment> viewComment(Integer commentId) {
            return commentRepository.findById(commentId);
    }

    public List<Comment> listComments() {
        return commentRepository.findAll();
    }

    public void editComment(Comment comment){
        commentRepository.edit(comment);
    }

    public void deleteComment(Comment comment){
        commentRepository.deleteById(comment.getComment_id());
    }
}