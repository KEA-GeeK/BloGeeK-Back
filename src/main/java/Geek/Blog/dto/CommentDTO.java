package Geek.Blog.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDTO {
    private Long comment_id = null;
    private String contents;
    private Long author_id;
    private Long post_id;

    public CommentDTO() {
    }
}
