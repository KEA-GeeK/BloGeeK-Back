package Geek.Blog.entity;

import Geek.Blog.dto.FollowDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Follow")
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
    private Long id;

    @Column
    private Long follower_id;

    @Column
    private Long following_id;

    public static Follow toFollowEntity(FollowDTO followDTO){
        Follow followEntity = new Follow();
        followEntity.setId(followDTO.getId());
        followEntity.setFollower_id(followDTO.getFollower_id());
        followEntity.setFollowing_id(followDTO.getFollowing_id());
        return followEntity;
    }
}
