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
    public String createComment(@RequestBody CommentDTO comment) {
        Integer result = commentservice.upload(comment);
        if (result == -1 ){
            return "Upload Failed";
        }
        else {
            return "Upload complete at ID " + result;
        }
    }

    @GetMapping("/all")
    public List<Comment> getCommentList() {
        return commentservice.listComments();
    }

    @GetMapping("/{commentId}")
    public Comment viewComment(@PathVariable Integer commentId) {
        return commentservice.viewComment(commentId).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 댓글입니다."));
    }

    @DeleteMapping("/{commentId}")
    public String deleteComment(@PathVariable Integer commentId) {
        Comment comment = commentservice.viewComment(commentId).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 댓글입니다."));
        commentservice.deleteComment(comment);
        return "Deleted successfully";
    }

    @PatchMapping("/{commentId}")
    public Comment editComment(@PathVariable Integer commentId, @RequestBody CommentDTO form) {  // @PathVariable 및 @RequestBody 사용
        Comment comment = commentservice.viewComment(commentId).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 게시글입니다."));
        if (form.getContents() == null || form.getContents().isBlank()) {
            throw new EntityNotFoundException("입력값이 잘못되었습니다.");
        }

        comment.setContents(form.getContents());
        commentservice.editComment(comment);
        return comment;
    }
}
