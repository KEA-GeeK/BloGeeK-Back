package Geek.Blog.repository;

import Geek.Blog.dto.ReplyDTO;
import Geek.Blog.entity.Reply;

import java.util.List;
import java.util.Optional;

public interface ReplyRepository {
    Reply upload(ReplyDTO replyDTO);
    Optional<Reply> findById(Long id);
    List<Reply> findAll();
    List<Reply> findCommentReply(Long commentId);
    Reply edit(Reply reply);
    Integer deleteById(Long id);
}