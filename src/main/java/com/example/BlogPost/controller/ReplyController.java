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
    public Integer createReply(@RequestBody ReplyDTO replyDTO) {
        return replyService.upload(replyDTO);
    }

    @GetMapping("/all")
    public List<Reply> getReplyList() {
        return replyService.listReplies();
    }

    @GetMapping("/{id}")
    public Reply viewReply(@PathVariable Integer id) {
        return replyService.viewReply(id).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 답글입니다."));
    }

    @DeleteMapping("/{id}")
    public String deleteReply(@PathVariable Integer id) {
        Reply reply = replyService.viewReply(id).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 답글입니다."));
        replyService.deleteReply(reply);
        return "Deleted successfully";
    }

    @PatchMapping("/edit/{id}")
    public Reply editReply(@PathVariable Integer id, @RequestBody ReplyDTO form) {  // @PathVariable 및 @RequestBody 사용
        Reply reply = replyService.viewReply(id).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 게시글입니다."));
        if (form.getContents() == null || form.getContents().isBlank()) {
            throw new EntityNotFoundException("입력값이 잘못되었습니다.");
        }

        reply.setContents(form.getContents());
        replyService.editReply(reply);
        return reply;
    }
}
