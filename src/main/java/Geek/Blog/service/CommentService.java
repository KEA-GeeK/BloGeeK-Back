package Geek.Blog.service;

import Geek.Blog.repository.CommentRepository;
import Geek.Blog.dto.CommentDTO;
import Geek.Blog.entity.Comment;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    /**
     * 기능
     **/
    public Comment upload(CommentDTO commentDTO) {
        try {
            Comment comment = commentRepository.upload(commentDTO);
            return comment;
        } catch (Exception e) {
            return null;
        }
    }


    public Optional<Comment> viewComment(Long commentId) {
            return commentRepository.findById(commentId);
    }

    public List<Comment> listCommentsOfPost(Long postId) {
        return commentRepository.findPostComment(postId);
    }

    public void editComment(Comment comment){
        commentRepository.edit(comment);
    }

    public void deleteComment(Comment comment){
        commentRepository.deleteById(comment.getComment_id());
    }
}
