package com.example.BlogPost.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDTO {
    private Integer comment_id = null;
    private String contents;
    private Integer post_id;
    //외래키
//    private Integer author_id;
//    private Integer category_id;

    public CommentDTO(Integer comment_id, String contents, Integer post_id) {
        this.comment_id = comment_id;
        this.contents = contents;
        this.post_id = post_id;
        //외래키
//        this.author_id = author_id;
//        this.category_id = category_id;
    }
    public CommentDTO() {
    }
}
