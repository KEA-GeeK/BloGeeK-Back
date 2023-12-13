package Geek.Blog.service;

import Geek.Blog.dto.PostDTO;
import Geek.Blog.entity.Post;
import Geek.Blog.repository.PostRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public Optional<Post> upload(PostDTO postDTO) {
        return postRepository.upload(postDTO);
    }

    public Optional<Post> viewPost(Long postId) {
            return postRepository.findById(postId);
    }

    public List<Post> listPosts() {
        return postRepository.findAll();
    }
    public List<Post> listPostsByCategory() {
        return postRepository.findCategoryPost();
    }

    public void editPost(Post post){
        postRepository.edit(post);
    }

    public void deletePost(Post post){
        postRepository.deleteById(post.getPost_id());
    }
}
