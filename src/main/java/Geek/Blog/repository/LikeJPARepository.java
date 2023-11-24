package Geek.Blog.repository;

import Geek.Blog.entity.Post;
import Geek.Blog.dto.LikeDTO;
import Geek.Blog.entity.Like;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class LikeJPARepository implements LikeRepository{

    private final EntityManager em;
    private final PostRepository postRepository;

    public LikeJPARepository(EntityManager em, PostRepository postRepository) {
        this.em = em;
        this.postRepository = postRepository;
    }

    @Override
    public Like add(LikeDTO likeDTO) {
        Like like = new Like();
        like.setPost(postRepository.findById(likeDTO.getPost_id())
                .orElseThrow(() -> new EntityNotFoundException("Post not found with ID: " + likeDTO.getPost_id())));

        like.setMember_id(likeDTO.getMember_id());

        return like;
    }

        @Override
        public Optional<Like> findById(Long id) {
            Like like = em.find(Like.class, id);
            return Optional.ofNullable(like);
        }

        @Override
        public Optional<Like> findByPostMember(Long postId, Long memberId) {
            Post post = postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException("Invaild Post."));

            Query query = em.createQuery("SELECT l FROM Like l WHERE l.post = :post AND l.member_id = :memberId");
            query.setParameter("post", post);
            query.setParameter("memberId", memberId);

            try {
                return Optional.ofNullable((Like) query.getSingleResult());
            } catch (NoResultException e) {
                return Optional.empty();
            }
        }

    @Override
    public List<Like> findAll() {
        return em.createQuery("SELECT l FROM Like l", Like.class).getResultList();
    }

    @Override
    public Integer deleteById(Long id) {
        Query query = em.createQuery("DELETE FROM Like l WHERE l.like_id = :id");
        query.setParameter("id", id);

        return query.executeUpdate();
    }
}
