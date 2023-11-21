package com.example.BlogPost.repository;

import com.example.BlogPost.DTO.PostDTO;
import com.example.BlogPost.entity.Post;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PostJPARepository implements PostRepository{

    private final EntityManager em;

    public PostJPARepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Post upload(PostDTO uploadDTO) {
        Post post = new Post();
        post.importPostData(uploadDTO);
        em.persist(post);

        return post;
    }

    @Override
    public Optional<Post> findById(Integer id) {
        Post post = em.find(Post.class, id);
        return Optional.ofNullable(post);
    }

    @Override
    public Optional<Post> findByTitle(String title) {
        List<Post> result = em.createQuery("select p from Post p where p.post_title = :title", Post.class)
                .setParameter("title", title).getResultList();
        return result.stream().findAny();
    }

    @Override
    public List<Post> findAll() {
        return em.createQuery("SELECT p FROM Post p", Post.class).getResultList();
    }

    @Override
    public Post edit(Post post) {
        Query query =  em.createQuery("UPDATE Post p SET p.post_title = :newTitle, p.contents = :newContent WHERE p.post_id = :id");
        query.setParameter("newTitle", post.getPost_title());
        query.setParameter("newContent", post.getContents());
        query.setParameter("id", post.getPost_id());

        query.executeUpdate();

        return post;
    }

    @Override
    public Integer deleteById(Integer id) {
        Query query = em.createQuery("DELETE FROM Post p WHERE p.post_id = :id");
        query.setParameter("id", id);

        return query.executeUpdate();
    }
}
