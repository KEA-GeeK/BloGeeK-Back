package com.example.BlogPost.controller;

import com.example.BlogPost.DTO.ReplyDTO;
import com.example.BlogPost.entity.Reply;
import com.example.BlogPost.service.ReplyService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts/reply")
public class ReplyController {

    private final ReplyService replyService;

    @Autowired
    public ReplyController(ReplyService replyService) {
        this.replyService = replyService;
    }

    @PostMapping("/write")
    public Reply createReply(@RequestBody ReplyDTO replyDTO) {
        Reply reply = replyService.upload(replyDTO);

        if (reply == null) {
            throw new RuntimeException("답글 등록에 실패했습니다.");
        }
        else{
            return reply;
        }



    }

    @GetMapping("/all")
    public List<Reply> getReplyList() {
        return replyService.listReplies();
    }

    @GetMapping("/{replyId}")
    public Reply viewReply(@PathVariable Long replyId) {
        return replyService.viewReply(replyId).orElseThrow(() -> new EntityNotFoundException("Invalid ID"));
    }

    @DeleteMapping("/{replyId}")
    public String deleteReply(@PathVariable Long replyId) {
        Reply reply = replyService.viewReply(replyId).orElseThrow(() -> new EntityNotFoundException("Invalid ID"));
        replyService.deleteReply(reply);
        return "Deleted successfully";
    }

    @PatchMapping("/{replyId}")
    public Reply editReply(@PathVariable Long replyId, @RequestBody ReplyDTO form) {  // @PathVariable 및 @RequestBody 사용
        Reply reply = replyService.viewReply(replyId).orElseThrow(() -> new EntityNotFoundException("Invalid ID"));
        if (form.getContents() == null || form.getContents().isBlank()) {
            throw new EntityNotFoundException("Invalid Input");
        }

        reply.setContents(form.getContents());
        replyService.editReply(reply);
        return reply;
    }
}
