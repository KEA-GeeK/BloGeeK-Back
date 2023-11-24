package Geek.Blog.repository;

import Geek.Blog.dto.LikeDTO;
import Geek.Blog.entity.Like;

import java.util.List;
import java.util.Optional;

public interface LikeRepository {
    Like add(LikeDTO likeDTO);
    Optional<Like> findById(Long id);

    Optional<Like> findByPostMember(Long postId, Long memberId);
    List<Like> findAll();
    Integer deleteById(Long id);
}