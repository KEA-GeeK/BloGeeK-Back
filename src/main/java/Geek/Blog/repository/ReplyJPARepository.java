package Geek.Blog.repository;

import Geek.Blog.dto.ReplyDTO;
import Geek.Blog.entity.Reply;
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
    private final MemberRepository memberRepository;

    public ReplyJPARepository(EntityManager em, CommentRepository commentRepository, MemberRepository memberRepository) {
        this.em = em;
        this.commentRepository = commentRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public Reply upload(ReplyDTO replyDTO) {
        Reply reply = new Reply();
        reply.setContents(replyDTO.getContents());
        reply.setComment(commentRepository.findById(replyDTO.getComment_id())
                .orElseThrow(() -> new EntityNotFoundException("Comment not found with ID: " + replyDTO.getComment_id())));
        reply.setAuthor(memberRepository.findById(replyDTO.getAuthor_id())
                .orElseThrow(() -> new EntityNotFoundException("Member not found with ID: " + replyDTO.getAuthor_id())));

        em.persist(reply);
        return reply;
    }

    @Override
    public Optional<Reply> findById(Long id) {
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
    public Integer deleteById(Long id) {
        Query query = em.createQuery("DELETE FROM Reply r WHERE r.reply_id = :id");
        query.setParameter("id", id);

        return query.executeUpdate();
    }
}
