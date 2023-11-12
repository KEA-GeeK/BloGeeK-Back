package com.example.BlogPost.repository;

import com.example.BlogPost.DTO.ReplyDTO;
import com.example.BlogPost.entity.Comment;
import com.example.BlogPost.entity.Reply;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ReplyJPARepository implements ReplyRepository{

    private final CommentRepository commentRepository;
    private final EntityManager em;

    public ReplyJPARepository(CommentRepository commentRepository, EntityManager em) {
        this.commentRepository = commentRepository;
        this.em = em;
    }

    @Override
    public ReplyDTO upload(ReplyDTO replyDTO) {
        Reply reply = new Reply();
        reply.setContents(replyDTO.getContents());

        Comment comment = commentRepository.findById(replyDTO.getComment_id())
                .orElseThrow(() -> new EntityNotFoundException("Comment not found"));
        reply.setComment(comment);

        em.persist(reply);
        return replyDTO;
    }

    @Override
    public Optional<Reply> findById(Integer id) {
        Reply reply = em.find(Reply.class, id);
        return Optional.ofNullable(reply);
    }

    @Override
    public List<Reply> findAll() {
        return em.createQuery("SELECT r FROM Reply r", Reply.class).getResultList();
    }

    @Override
    public Reply edit(Reply reply) {
        Query query =  em.createQuery("UPDATE Reply r SET r.contents = :newContent WHERE r.reply_id = :id");
        query.setParameter("newContent", reply.getContents());
        query.setParameter("id", reply.getReply_id());
        query.executeUpdate();

        return reply;
    }

    @Override
    public Integer deleteById(Integer id) {
        Query query = em.createQuery("DELETE FROM Reply r WHERE r.reply_id = :id");
        query.setParameter("id", id);

        return query.executeUpdate();
    }
}
