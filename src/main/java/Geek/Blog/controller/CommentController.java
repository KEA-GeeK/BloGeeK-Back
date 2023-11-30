package Geek.Blog.controller;

import Geek.Blog.Response.CommentResponseDTO;
import Geek.Blog.dto.CommentDTO;
import Geek.Blog.dto.CommentEditDTO;
import Geek.Blog.entity.Comment;
import Geek.Blog.service.CommentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/posts/{postId}/comment")
public class CommentController {

    private final CommentService commentservice;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentservice = commentService;
    }


    @PostMapping("/write")
    public ResponseEntity<Object> createComment(@PathVariable("postId") Long postId, @RequestBody CommentDTO commentDTO) {
        commentDTO.setPost_id(postId);
        Comment comment = commentservice.upload(commentDTO);

        if (comment == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(new CommentResponseDTO(comment));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getCommentList(@PathVariable("postId") Long postId) {
        try {
            List<Comment> comments = commentservice.listCommentsOfPost(postId);
            if (comments.isEmpty()){
                throw new EntityNotFoundException("Post not found with ID: " + postId);
            }

            List<CommentResponseDTO> commentResponseDTOS = comments.stream()
                    .map(CommentResponseDTO::new) // Comment 객체를 CommentResponseDTO 객체로 변환
                    .collect(Collectors.toList()); // 결과를 List로 수집

            return ResponseEntity.ok(commentResponseDTOS);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 내부 오류가 발생했습니다.");
        }
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<?> viewComment(@PathVariable("commentId") Long commentId) {
        try {
            Comment comment = commentservice.viewComment(commentId).orElseThrow(() -> new EntityNotFoundException("Post not found with ID: " + commentId));
            return ResponseEntity.ok(new CommentResponseDTO(comment));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable("commentId") Long commentId) {
        try {
            Comment comment = commentservice.viewComment(commentId).orElseThrow(() -> new EntityNotFoundException("Invalid ID"));
            commentservice.deleteComment(comment);
            return ResponseEntity.ok("Deleted successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found with ID: " + commentId);
        }
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<?> editComment(@PathVariable("commentId") Long commentId, @RequestBody CommentEditDTO form) {  // @PathVariable 및 @RequestBody 사용
        try {
            Comment comment = commentservice.viewComment(commentId).orElseThrow(() -> new EntityNotFoundException("Invalid ID"));
            if (form.getContents() == null || form.getContents().isBlank()) {
                throw new EntityNotFoundException("Invalid Input");
            }

            comment.setContents(form.getContents());
            commentservice.editComment(comment);
            return ResponseEntity.ok(new CommentResponseDTO(comment));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found with ID: " + commentId);
        }
    }
}
