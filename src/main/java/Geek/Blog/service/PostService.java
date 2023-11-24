package Geek.Blog.service;

import Geek.Blog.entity.Post;
import Geek.Blog.repository.PostRepository;
import Geek.Blog.dto.PostDTO;
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
    public Post upload(PostDTO postDTO) {
        try {
            Post post = postRepository.upload(postDTO);
            return post;
        } catch (Exception e) {
            return null;
        }
    }

    public Optional<Post> viewPost(Long postId) {
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
