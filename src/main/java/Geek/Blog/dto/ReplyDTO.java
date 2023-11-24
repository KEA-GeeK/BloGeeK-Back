package Geek.Blog.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReplyDTO {
    private Long reply_id = null;
    private String contents;
    private Long author_id;
    private Long comment_id;
    public ReplyDTO() {
    }
}
