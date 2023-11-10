package com.example.BlogPost;

import com.example.BlogPost.repository.*;
import com.example.BlogPost.service.CommentService;
import com.example.BlogPost.service.PostService;
import com.example.BlogPost.service.ReplyService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

//    private DataSource dataSource;
//
//    @Autowired
//    public SpringConfig(DataSource dataSource) {
//        this.dataSource = dataSource;
//    }

    private EntityManager em;

    @Autowired
    public SpringConfig(EntityManager em) {
        this.em = em;
    }

    @Bean
    public PostRepository postRepository(){
        //return new MemoryPostRepository();
        //return new JDBCPostRepository(dataSource);
        //return  new JDBCTemplatePostRepository(dataSource);
        return new PostJPARepository(em);
    }

    @Bean
    public CommentRepository commentRepository(){
        return new CommentJPARepository(em);
    }

    @Bean
    public ReplyRepository replyRepository(){
        return new ReplyJPARepository(em);
    }

    @Bean
    public PostService postService(){
        return new PostService(postRepository());
    }

    @Bean
    public CommentService commentService(){
        return new CommentService(commentRepository());
    }

    @Bean
    public ReplyService replyService(){
        return new ReplyService(replyRepository());
    }


}
