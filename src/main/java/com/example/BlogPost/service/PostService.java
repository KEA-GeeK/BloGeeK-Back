package com.example.BlogPost.service;

import com.example.BlogPost.DTO.PostDTO;
import com.example.BlogPost.entity.Post;
import com.example.BlogPost.repository.PostRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//TODO DB에서 받아온 데이터가 유효한지 try catch
@Transactional
@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    /**
     * 기능
     **/
    public Integer upload(PostDTO post) {
        postRepository.upload(post);
        return post.getPost_id();
    }

    public Optional<Post> viewPost(Integer postId) {
            return postRepository.findById(postId);
    }

    public List<Post> listPosts() {
        return postRepository.findAll();
    }

    public void editPost(Post post){
        postRepository.edit(post);
    }

    public void deletePost(Post post){
        postRepository.deleteById(post.getPost_id());
    }
}
