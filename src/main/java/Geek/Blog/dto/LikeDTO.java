package Geek.Blog.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikeDTO {
    private Long like_id = null;
    private Long post_id;
    private Long member_id;

    public LikeDTO() {
    }
}
