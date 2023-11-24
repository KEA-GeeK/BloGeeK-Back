package Geek.Blog.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDTO {
    private Long post_id = null;
    private String post_title;
    private String contents;
    private Long author_id;
    private Long category_id;
    
    public PostDTO() {
    }
}
