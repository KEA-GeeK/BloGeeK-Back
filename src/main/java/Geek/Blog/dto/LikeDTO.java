package Geek.Blog.dto;

import Geek.Blog.entity.Like;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LikeDTO {
    private Long like_id = null;
    private Long post_id;
    private Long member_id;

    /** LikeRequestDTO 겸용 **/
    public LikeDTO(Like like) {
        like_id = like.getLike_id();
        post_id = like.getPost().getPost_id();
        member_id = like.getMember().getId();
    }
}
