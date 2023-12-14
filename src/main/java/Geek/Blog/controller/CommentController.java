package Geek.Blog.controller;

import Geek.Blog.Response.CommentResponseDTO;
import Geek.Blog.dto.CommentDTO;
import Geek.Blog.dto.CommentDeleteDTO;
import Geek.Blog.dto.CommentEditDTO;
import Geek.Blog.entity.Comment;
import Geek.Blog.service.CommentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user/comment")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }


    @PostMapping("/write")
    public ResponseEntity<Object> createComment(@RequestBody CommentDTO commentDTO) {

        commentDTO.setPost_id(commentDTO.getPost_id());
        Comment comment = commentService.upload(commentDTO);

        if (comment == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("게시글 업로드를 실패했습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(new CommentResponseDTO(comment));
        }
    }

    @GetMapping("/list/{postId}")
    public ResponseEntity<?> getCommentList(@PathVariable("postId") Long postId) {
        try {
            List<Comment> comments = commentService.listCommentsOfPost(postId);
            if (comments.isEmpty()){
                throw new EntityNotFoundException("Comment not found with PostID: " + postId);
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

//    @GetMapping("/{commentId}")
//    public ResponseEntity<?> viewComment(@PathVariable("commentId") Long commentId) {
//        try {
//            Comment comment = commentservice.viewComment(commentId).orElseThrow(() -> new EntityNotFoundException("Post not found with ID: " + commentId));
//            return ResponseEntity.ok(new CommentResponseDTO(comment));
//        } catch (EntityNotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
//        }
//    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId, @RequestBody CommentDeleteDTO commentDTO) {
        try{
            Comment comment = commentService.viewComment(commentId).orElseThrow(() -> new EntityNotFoundException("Invalid ID"));

            if (Objects.equals(commentDTO.getClaimer_id(), comment.getAuthor().getId())) {
                commentService.deleteComment(comment);
                return ResponseEntity.ok("Deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Delete Denied");
            }
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comment not found with ID: " + commentId);
        }
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<?> editComment(@PathVariable("commentId") Long commentId, @RequestBody CommentEditDTO form) {  // @PathVariable 및 @RequestBody 사용
        try {
            Comment comment = commentService.viewComment(commentId).orElseThrow(() -> new EntityNotFoundException("Invalid ID"));
            if (form.getContents() == null || form.getContents().isBlank()) {
                return ResponseEntity.badRequest().body("Invalid input");
            }

            if (Objects.equals(form.getClaimer_id(), comment.getAuthor().getId())){
                comment.setContents(form.getContents());
                commentService.editComment(comment);
                return ResponseEntity.ok(new CommentResponseDTO(comment));
            }
            else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Edit Denied");
            }
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comment not found with ID: " + commentId);
        }
    }
}
