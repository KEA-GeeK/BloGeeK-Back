package Geek.Blog.controller;

import Geek.Blog.Response.ReplyResponseDTO;
import Geek.Blog.dto.ReplyDTO;
import Geek.Blog.dto.ReplyEditDTO;
import Geek.Blog.entity.Reply;
import Geek.Blog.service.ReplyService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/posts/{commentId}/reply")
public class ReplyController {

    private final ReplyService replyService;

    @Autowired
    public ReplyController(ReplyService replyService) {
        this.replyService = replyService;
    }

    @PostMapping("/write")
    public ResponseEntity<?> createReply(@PathVariable("commentId") Long commentId, @RequestBody ReplyDTO replyDTO) {
        replyDTO.setComment_id(commentId);
        Reply reply = replyService.upload(replyDTO);

        if (reply == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("답글 등록에 실패했습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(new ReplyResponseDTO(reply));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<ReplyResponseDTO>> getReplyList(@PathVariable("commentId") Long commentId) {
        List<Reply> replies = replyService.listRepliesOfComment(commentId);
        List<ReplyResponseDTO> replyResponseDTOS = replies.stream().map(ReplyResponseDTO::new).collect(Collectors.toList());

        return ResponseEntity.ok(replyResponseDTOS);
    }

    @GetMapping("/{replyId}")
    public ResponseEntity<?> viewReply(@PathVariable Long replyId) {
        try {
            Reply reply = replyService.viewReply(replyId).orElseThrow(() -> new EntityNotFoundException("Invalid ID"));
            return ResponseEntity.ok(new ReplyResponseDTO(reply));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{replyId}")
    public ResponseEntity<String> deleteReply(@PathVariable Long replyId) {
        try {
            Reply reply = replyService.viewReply(replyId).orElseThrow(() -> new EntityNotFoundException("Invalid ID"));
            replyService.deleteReply(reply);
            return ResponseEntity.ok("Deleted successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reply not found with ID: " + replyId);
        }
    }

    @PatchMapping("/{replyId}")
    public ResponseEntity<?> editReply(@PathVariable Long replyId, @RequestBody ReplyEditDTO form) {
        try {
            Reply reply = replyService.viewReply(replyId).orElseThrow(() -> new EntityNotFoundException("Invalid ID"));
            if (form.getContents() == null || form.getContents().isBlank()) {
                return ResponseEntity.badRequest().body("Invalid input");
            }

            reply.setContents(form.getContents());
            replyService.editReply(reply);
            return ResponseEntity.ok(new ReplyResponseDTO(reply));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reply not found with ID: " + replyId);
        }
    }
}
