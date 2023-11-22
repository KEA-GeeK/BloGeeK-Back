package com.example.BlogPost.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDTO {
    private Long comment_id = null;
    private String contents;
    private Long author_id;
    private Long category_id;

    public CommentDTO() {
    }
}
