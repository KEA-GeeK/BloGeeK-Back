package Geek.Blog.repository;

import Geek.Blog.dto.CommentDTO;
import Geek.Blog.dto.CommentSentimentDTO;
import Geek.Blog.entity.Comment;
import Geek.Blog.entity.Post;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CommentJPARepository implements CommentRepository{

    private final EntityManager em;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public CommentJPARepository(EntityManager em, PostRepository postRepository, MemberRepository memberRepository) {
        this.em = em;
        this.postRepository = postRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public Comment upload(CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setContents(commentDTO.getContents());
        comment.setPost(postRepository.findById(commentDTO.getClaimer_id())
                .orElseThrow(() -> new EntityNotFoundException("Post not found with ID: " + commentDTO.getClaimer_id())));
        comment.setAuthor(memberRepository.findById(commentDTO.getClaimer_id())
                .orElseThrow(() -> new EntityNotFoundException("Member not found with ID: " + commentDTO.getClaimer_id())));

        em.persist(comment);
        return comment;
    }

    @Override
    public Optional<Comment> findById(Long id) {
        Comment comment = em.find(Comment.class, id);
        return Optional.ofNullable(comment);
    }

    @Override
    public List<Comment> findPostComment(Long postId) {
        Post post = postRepository.findById(postId).orElse(null);
        if (post == null) {
            return new ArrayList<>();
        }

        Query query = em.createQuery("SELECT c FROM Comment c WHERE c.post = :post", Comment.class);
        query.setParameter("post", post);

        @SuppressWarnings("unchecked")
        List<Comment> result = query.getResultList();
        return  result;
    }

    public CommentSentimentDTO checkCommentStyle(){ //DL로 요청 보내기
        return null;
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
    public Integer deleteById(Long id) {
        Query query = em.createQuery("DELETE FROM Comment c WHERE c.comment_id = :id");
        query.setParameter("id", id);

        return query.executeUpdate();
    }
}
