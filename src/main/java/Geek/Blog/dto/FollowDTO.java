package Geek.Blog.dto;

import Geek.Blog.entity.Follow;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class FollowDTO {
    private Long id;
    private Long follower_id;
    private Long following_id;

    public static FollowDTO tofollowDTO(Follow followEntity){
        FollowDTO followDTO = new FollowDTO();
        followDTO.setId(followEntity.getId());
        followDTO.setFollower_id(followEntity.getFollower_id());
        followDTO.setFollowing_id(followEntity.getFollowing_id());

        return followDTO;
    }
}
