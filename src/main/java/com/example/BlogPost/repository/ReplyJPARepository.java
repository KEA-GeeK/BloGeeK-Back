package com.example.BlogPost.repository;

import com.example.BlogPost.DTO.ReplyDTO;
import com.example.BlogPost.entity.Reply;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ReplyJPARepository implements ReplyRepository{

    private final EntityManager em;
    private final CommentRepository commentRepository;

    public ReplyJPARepository(EntityManager em, CommentRepository commentRepository) {
        this.em = em;
        this.commentRepository = commentRepository;
    }

    @Override
    public Reply upload(ReplyDTO replyDTO) {
        Reply reply = new Reply();
        reply.setContents(replyDTO.getContents());
        reply.setComment(commentRepository.findById(replyDTO.getComment_id())
                .orElseThrow(() -> new EntityNotFoundException("Comment not found with ID: " + replyDTO.getComment_id())));
        em.persist(reply);
        return reply;
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
