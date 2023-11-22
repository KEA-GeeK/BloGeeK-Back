package com.example.BlogPost.repository;

import com.example.BlogPost.DTO.ReplyDTO;
import com.example.BlogPost.entity.Reply;

import java.util.List;
import java.util.Optional;

public interface ReplyRepository {
    Reply upload(ReplyDTO replyDTO);
    Optional<Reply> findById(Long id);
    List<Reply> findAll();
    Reply edit(Reply reply);
    Integer deleteById(Long id);
}