package com.example.BlogPost.controller;

import com.example.BlogPost.DTO.CommentDTO;
import com.example.BlogPost.entity.Comment;
import com.example.BlogPost.service.CommentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts/comment")
public class CommentController {

    private final CommentService commentservice;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentservice = commentService;
    }


    @PostMapping("/write")
    public Comment createComment(@RequestBody CommentDTO commentDTO) {
        Comment comment = commentservice.upload(commentDTO);

        if (comment == null) {
            throw new RuntimeException("댓글 등록에 실패했습니다.");
        }
        else{
            return comment;
        }
    }

    @GetMapping("/all")
    public List<Comment> getCommentList() {
        return commentservice.listComments();
    }

    @GetMapping("/{commentId}")
    public Comment viewComment(@PathVariable Long commentId) {
        return commentservice.viewComment(commentId).orElseThrow(() -> new EntityNotFoundException("Invalid ID"));
    }

    @DeleteMapping("/{commentId}")
    public String deleteComment(@PathVariable Long commentId) {
        Comment comment = commentservice.viewComment(commentId).orElseThrow(() -> new EntityNotFoundException("Invalid ID"));
        commentservice.deleteComment(comment);
        return "Deleted successfully";
    }

    @PatchMapping("/{commentId}")
    public Comment editComment(@PathVariable Long commentId, @RequestBody CommentDTO form) {  // @PathVariable 및 @RequestBody 사용
        Comment comment = commentservice.viewComment(commentId).orElseThrow(() -> new EntityNotFoundException("Invalid ID"));
        if (form.getContents() == null || form.getContents().isBlank()) {
            throw new EntityNotFoundException("Invalid Input");
        }

        comment.setContents(form.getContents());
        commentservice.editComment(comment);
        return comment;
    }
}
