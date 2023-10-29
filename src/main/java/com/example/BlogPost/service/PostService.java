package com.example.BlogPost.service;

import com.example.BlogPost.entity.Post;
import com.example.BlogPost.repository.PostRepository;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

//TODO DB에서 받아온 데이터가 유효한지 try catch
@Transactional
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    /**
     * 기능
     **/
    public Integer upload(Post post) {
        postRepository.upload(post);
        return post.getPost_id();
    }

    public Optional<Post> viewPost(Integer postId) {
            return postRepository.findById(postId);
    }

    public List<Post> listPosts() {
        return postRepository.findAll();
    }

    public Post editPost(Post post){
        return postRepository.edit(post);
    }

    public Integer deletePost(Post post){
        return postRepository.deleteById(post.getPost_id());
    }
}
