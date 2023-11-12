package com.example.BlogPost.repository;

import com.example.BlogPost.DTO.CommentDTO;
import com.example.BlogPost.entity.Comment;
import com.example.BlogPost.entity.Post;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CommentJPARepository implements CommentRepository{

    private final PostRepository postRepository;
    private final EntityManager em;

    public CommentJPARepository(PostRepository postRepository, EntityManager em) {
        this.postRepository = postRepository;
        this.em = em;
    }

    @Override
    public CommentDTO upload(CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setContents(commentDTO.getContents());

        Post post = postRepository.findById(commentDTO.getPost_id())
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));
        comment.setPost(post);

        em.persist(comment);
        return commentDTO;
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
