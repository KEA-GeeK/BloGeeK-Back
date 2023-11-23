package com.example.BlogPost.repository;

import com.example.BlogPost.DTO.BlogDTO;
import com.example.BlogPost.entity.Blog;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class BlogJPARepository implements BlogRepository{

    private final EntityManager em;

    public BlogJPARepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Blog create(BlogDTO blogDTO) {
        Blog blog = new Blog();

        blog.setBlog_name(blogDTO.getBlog_name());
        blog.setAbout_blog(blogDTO.getAbout_blog());
        blog.setProfile_picture(blogDTO.getProfilePicture());
        blog.setOwner_id(blogDTO.getOwner_id());

        em.persist(blog);
        return blog;
    }

    @Override
    public Optional<Blog> findById(Integer id) {
        Blog blog = em.find(Blog.class, id);
        return Optional.ofNullable(blog);
    }

    @Override
    public Blog edit(Blog blog) {
        Query query = em.createQuery("UPDATE Blog b SET b.blog_name = :newBlogName, b.about_blog = :newAboutBlog, b.profile_picture = :newProfilePicture WHERE b.blog_id = :id");
        query.setParameter("newBlogName", blog.getBlog_name());
        query.setParameter("newAboutBlog", blog.getAbout_blog());
        query.setParameter("newProfilePicture", blog.getProfile_picture());
        query.setParameter("id", blog.getBlog_id());

        query.executeUpdate();
        return blog;
    }

    @Override
    public Integer deleteById(Integer id) {
        Query query = em.createQuery("DELETE FROM Blog b WHERE b.blog_id = :id");
        query.setParameter("id", id);

        return query.executeUpdate();
    }
}
