package com.example.BlogPost;

import com.example.BlogPost.repository.JPAPostRepository;
import com.example.BlogPost.repository.PostRepository;
import com.example.BlogPost.service.PostService;
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
        return new JPAPostRepository(em);
    }

    @Bean
    public PostService postService(){
        return new PostService(postRepository());
    }


}
