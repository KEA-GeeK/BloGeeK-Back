package Geek.Blog.controller;

import Geek.Blog.dto.CommentDTO;
import Geek.Blog.dto.CommentResponseDTO;
import Geek.Blog.entity.Comment;
import Geek.Blog.service.CommentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
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
    public CommentResponseDTO createComment(@PathVariable("postId") Long postId, @RequestBody CommentDTO commentDTO) {
        commentDTO.setPost_id(postId);
        Comment comment = commentservice.upload(commentDTO);

        if (comment == null) {
            throw new RuntimeException("댓글 등록에 실패했습니다.");
        }
        else{
            return new CommentResponseDTO(comment);
        }
    }

    @GetMapping("/all")
    public List<CommentResponseDTO> getCommentList(@PathVariable("postId") Long postId) {
        List<Comment> comments = commentservice.listCommentsOfPost(postId);
        return comments.stream()
                .map(CommentResponseDTO::new) // Comment 객체를 CommentResponseDTO 객체로 변환
                .collect(Collectors.toList()); // 결과를 List로 수집
    }

    @GetMapping("/{commentId}")
    public CommentResponseDTO viewComment(@PathVariable("commentId") Long commentId) {
        return commentservice.viewComment(commentId) //Optional<Post>
                .map(CommentResponseDTO::new) // Post 객체를 PostResponseDTO로 변환
                .orElseThrow(() -> new EntityNotFoundException("Invalid ID"));
    }

    @DeleteMapping("/{commentId}")
    public String deleteComment(@PathVariable("commentId") Long commentId) {
        Comment comment = commentservice.viewComment(commentId).orElseThrow(() -> new EntityNotFoundException("Invalid ID"));
        commentservice.deleteComment(comment);
        return "Deleted successfully";
    }

    @PatchMapping("/{commentId}")
    public CommentResponseDTO editComment(@PathVariable("commentId") Long commentId, @RequestBody CommentDTO form) {  // @PathVariable 및 @RequestBody 사용
        Comment comment = commentservice.viewComment(commentId).orElseThrow(() -> new EntityNotFoundException("Invalid ID"));
        if (form.getContents() == null || form.getContents().isBlank()) {
            throw new EntityNotFoundException("Invalid Input");
        }

        comment.setContents(form.getContents());
        commentservice.editComment(comment);
        return new CommentResponseDTO(comment);
    }
}
