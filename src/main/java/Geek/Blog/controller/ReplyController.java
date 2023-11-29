package Geek.Blog.controller;

import Geek.Blog.dto.ReplyDTO;
import Geek.Blog.Response.ReplyResponseDTO;
import Geek.Blog.dto.ReplyEditDTO;
import Geek.Blog.entity.Reply;
import Geek.Blog.service.ReplyService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/posts/reply")
public class ReplyController {

    private final ReplyService replyService;

    @Autowired
    public ReplyController(ReplyService replyService) {
        this.replyService = replyService;
    }

    @PostMapping("/write")
    public ReplyResponseDTO createReply(@RequestBody ReplyDTO replyDTO) {
        Reply reply = replyService.upload(replyDTO);

        if (reply == null) {
            throw new RuntimeException("답글 등록에 실패했습니다.");
        }
        else{
            return new ReplyResponseDTO(reply);
        }
    }

    @GetMapping("/all")
    public List<ReplyResponseDTO> getReplyList() {
        List<Reply> replies = replyService.listReplies();
        return replies.stream()
                .map(ReplyResponseDTO::new) // Reply 객체를 ReplyResponseDTO 객체로 변환
                .collect(Collectors.toList()); // 결과를 List로 수집
    }

    @GetMapping("/{replyId}")
    public ReplyResponseDTO viewReply(@PathVariable Long replyId) {
        return replyService.viewReply(replyId) //Optional<Post>
                .map(ReplyResponseDTO::new) // Post 객체를 PostResponseDTO로 변환
                .orElseThrow(() -> new EntityNotFoundException("Invalid ID"));
    }

    @DeleteMapping("/{replyId}")
    public String deleteReply(@PathVariable Long replyId) {
        Reply reply = replyService.viewReply(replyId).orElseThrow(() -> new EntityNotFoundException("Invalid ID"));
        replyService.deleteReply(reply);
        return "Deleted successfully";
    }

    @PatchMapping("/{replyId}")
    public ReplyResponseDTO editReply(@PathVariable Long replyId, @RequestBody ReplyEditDTO form) {  // @PathVariable 및 @RequestBody 사용
        Reply reply = replyService.viewReply(replyId).orElseThrow(() -> new EntityNotFoundException("Invalid ID"));
        if (form.getContents() == null || form.getContents().isBlank()) {
            throw new EntityNotFoundException("Invalid Input");
        }

        reply.setContents(form.getContents());
        replyService.editReply(reply);
        return new ReplyResponseDTO(reply);
    }
}
