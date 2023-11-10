package com.example.BlogPost.repository;

import com.example.BlogPost.DTO.ReplyDTO;
import com.example.BlogPost.entity.Reply;

import java.util.List;
import java.util.Optional;

public interface ReplyRepository {
    ReplyDTO upload(ReplyDTO replyDTO);
    Optional<Reply> findById(Integer id);
    List<Reply> findAll();
    Reply edit(Reply reply);
    Integer deleteById(Integer id);
}