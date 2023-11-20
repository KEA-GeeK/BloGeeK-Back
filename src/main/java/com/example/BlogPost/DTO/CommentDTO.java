package com.example.BlogPost.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDTO {
    private Integer comment_id = null;
    private String contents;
    private Integer author_id;
    private Integer category_id;

    public CommentDTO() {
    }
}
