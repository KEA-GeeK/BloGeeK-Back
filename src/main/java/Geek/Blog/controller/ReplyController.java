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
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user/reply")
public class ReplyController {

    private final ReplyService replyService;

    @Autowired
    public ReplyController(ReplyService replyService) {
        this.replyService = replyService;
    }

    @PostMapping("/write")
    public ResponseEntity<?> createReply(@RequestBody ReplyDTO replyDTO) {
        Reply reply = replyService.upload(replyDTO);

        if (reply == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("답글 등록에 실패했습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(new ReplyResponseDTO(reply));
        }
    }

    @GetMapping("/list/{commentId}")
    public ResponseEntity<?> getReplyList(@PathVariable("commentId") Long commentId) {
        try {
            List<Reply> replies = replyService.listRepliesOfComment(commentId);
            if (replies.isEmpty()){
                throw new EntityNotFoundException("Comment not found with ID: " + commentId);
            }

            List<ReplyResponseDTO> replyResponseDTOS = replies.stream()
                    .map(ReplyResponseDTO::new) // Comment 객체를 CommentResponseDTO 객체로 변환
                    .collect(Collectors.toList()); // 결과를 List로 수집

            return ResponseEntity.ok(replyResponseDTOS);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 내부 오류가 발생했습니다.");
        }
    }

//    @GetMapping("/{replyId}")
//    public ResponseEntity<?> viewReply(@PathVariable Long replyId) {
//        try {
//            Reply reply = replyService.viewReply(replyId).orElseThrow(() -> new EntityNotFoundException("Invalid ID"));
//            return ResponseEntity.ok(new ReplyResponseDTO(reply));
//        } catch (EntityNotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
//        }
//    }

    @DeleteMapping("/{replyId}")
    public ResponseEntity<String> deleteReply(@PathVariable Long replyId, @RequestBody ReplyDTO replyDTO) {
        try {
            Reply reply = replyService.viewReply(replyId).orElseThrow(() -> new EntityNotFoundException("Invalid ID"));

            if (Objects.equals(replyDTO.getClaimer_id(), reply.getAuthor().getId())){
                replyService.deleteReply(reply);
                return ResponseEntity.ok("Deleted successfully");
            }
            else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Delete Denied");
            }


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

            if (Objects.equals(form.getClaimer_id(), reply.getAuthor().getId())){
                reply.setContents(form.getContents());
                replyService.editReply(reply);
                return ResponseEntity.ok(new ReplyResponseDTO(reply));
            }
            else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Edit Denied");
            }
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reply not found with ID: " + replyId);
        }
    }
}
