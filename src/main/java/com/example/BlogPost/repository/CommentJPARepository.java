package com.example.BlogPost.repository;

import com.example.BlogPost.DTO.CommentDTO;
import com.example.BlogPost.entity.Comment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CommentJPARepository implements CommentRepository{

    private final EntityManager em;
    private final PostRepository postRepository;

    public CommentJPARepository(EntityManager em, PostRepository postRepository) {
        this.em = em;
        this.postRepository = postRepository;
    }

    @Override
    public Comment upload(CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setContents(commentDTO.getContents());
        comment.setPost(postRepository.findById(commentDTO.getAuthor_id())
                .orElseThrow(() -> new EntityNotFoundException("Post not found with ID: " + commentDTO.getAuthor_id())));

        em.persist(comment);
        return comment;
    }

    @Override
    public Optional<Comment> findById(Integer id) {
        Comment comment = em.find(Comment.class, id);
        return Optional.ofNullable(comment);
    }

    @Override
    public List<Comment> findAll() {
        return em.createQuery("SELECT c FROM Comment c", Comment.class).getResultList();
    }

    @Override
    public Comment edit(Comment comment) {
        Query query =  em.createQuery("UPDATE Comment c SET c.contents = :newContent WHERE c.comment_id = :id");
        query.setParameter("newContent", comment.getContents());
        query.setParameter("id", comment.getComment_id());
        query.executeUpdate();

        return comment;
    }

    @Override
    public Integer deleteById(Integer id) {
        Query query = em.createQuery("DELETE FROM Comment c WHERE c.comment_id = :id");
        query.setParameter("id", id);

        return query.executeUpdate();
    }
}
